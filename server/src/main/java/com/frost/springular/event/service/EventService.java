package com.frost.springular.event.service;

import com.frost.springular.event.data.EventRepeat;
import com.frost.springular.event.data.model.EventModel;
import com.frost.springular.event.data.model.EventRepository;
import com.frost.springular.event.data.request.EventRequest;
import com.frost.springular.event.exception.CalendarEventException;
import com.frost.springular.shared.tuple.Pair;
import com.frost.springular.user.data.model.UserModel;
import com.frost.springular.user.service.UserService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

// * This implementation only works with non-repeated events
@Service
public class EventService {
  private static class SortByStartTime
      implements Comparator<EventModel> {
    public int compare(EventModel a, EventModel b) {
      return a.getStart().compareTo(b.getStart());
    }
  }

  private final SortByStartTime sortByStartTime = new SortByStartTime();
  private final EventRepository calendarEventRepository;
  private final UserService userService;
  private final ConversionService conversionService;

  public EventService(
      EventRepository calendarEventRepository,
      UserService userService,
      ConversionService conversionService) {
    this.calendarEventRepository = calendarEventRepository;
    this.userService = userService;
    this.conversionService = conversionService;
  }

  public Optional<EventModel> findById(String id) {
    return calendarEventRepository.findById(id);
  }

  public List<EventModel> getAllEvents(UserModel userEntity) {
    return calendarEventRepository
        .findByUserOrderByStart(userEntity);
    // .stream()
    // .sorted(sortByStartTime)
    // .toList();
  }

  public List<EventModel> filterEventsByInterval(
      String interval, Instant startTime) {
    return switch (interval) {
      case "week" -> filterEventsOfWeek(startTime);
      default -> new ArrayList<EventModel>();
    };
  }

  public EventModel create(EventRequest eventRequest) {
    EventModel newEvent = conversionService.convert(
        Pair.of(eventRequest, userService.getCurrentUser()),
        EventModel.class);

    if (!isEventInsertable(newEvent)) {
      throw new CalendarEventException("Time conflicted");
    }

    return calendarEventRepository.save(newEvent);
  }

  public EventModel update(String id, EventRequest eventRequest) {
    EventModel eventModel = findById(id)
        .orElseThrow(() -> new CalendarEventException(
            "Could not find calendar event"));

    eventModel.setTitle(eventRequest.getTitle());
    eventModel.setDescription(eventRequest.getDescription());
    eventModel.setColor(eventRequest.getColor());
    eventModel.setStart(eventRequest.getStart());
    eventModel.setDurationMinutes(eventRequest.getDurationMinutes());
    eventModel.setRepeat(eventRequest.getRepeat());
    eventModel.setRepeatEveryValue(eventRequest.getRepeatEvery().getValue());
    eventModel.setRepeatEveryUnit(eventRequest.getRepeatEvery().getUnit());

    // todo: CalendarEventRequest should also contain userEntity
    isEventInsertable(eventModel);

    return calendarEventRepository.save(eventModel);
  }

  public void delete(String id) {
    calendarEventRepository.deleteById(id);
  }

  private boolean isEventInsertable(EventModel calendarEvent) {
    List<EventModel> allEvents = getAllEvents(
        calendarEvent.getUser());

    if (allEvents.isEmpty()) {
      return true;
    }

    int index = Collections.binarySearch(allEvents, calendarEvent, sortByStartTime);
    int lowerIndex, upperIndex;

    if (index >= 0) {
      return false;
    } else {
      int insertionPoint = -index - 1;
      lowerIndex = insertionPoint - 1;
      upperIndex = insertionPoint;
    }

    if (lowerIndex >= 0) {
      Instant lowerBoundEndTime = allEvents.get(lowerIndex)
          .getStart()
          .plus(allEvents.get(lowerIndex).getDurationMinutes(), ChronoUnit.MINUTES);
      Instant eventStartTime = calendarEvent.getStart();
      if (eventStartTime.compareTo(lowerBoundEndTime) < 0) {
        return false;
      }
    }

    if (upperIndex < allEvents.size()) {
      Instant eventEndTime = calendarEvent
          .getStart()
          .plus(calendarEvent.getDurationMinutes(), ChronoUnit.MINUTES);
      Instant upperBoundStartTime = allEvents.get(upperIndex).getStart();
      if (eventEndTime.compareTo(upperBoundStartTime) > 0) {
        return false;
      }
    }

    return true;
  }

  private List<EventModel> filterEventsOfWeek(Instant startTime) {
    // todo: parallel streams
    return Stream
        .concat(calendarEventRepository
            .findByUserAndRepeatAndStartGreaterThanEqualAndStartLessThan(
                userService.getCurrentUser(), EventRepeat.none, startTime,
                startTime.plus(7, ChronoUnit.DAYS))
            .stream(),
            new ArrayList<EventModel>().stream())
        .sorted(sortByStartTime)
        .toList();
  }
}
