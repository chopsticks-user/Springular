package com.frost.springular.service;

import com.frost.springular.exception.CalendarEventException;
import com.frost.springular.model.CalendarEventModel;
import com.frost.springular.model.UserModel;
import com.frost.springular.repository.CalendarEventRepository;
import com.frost.springular.request.CalendarEventRequest;
import com.frost.springular.util.Pair;

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
public class CalendarEventService {
  private static class SortByStartTime
      implements Comparator<CalendarEventModel> {
    public int compare(CalendarEventModel a, CalendarEventModel b) {
      return a.getStart().compareTo(b.getStart());
    }
  }

  private final SortByStartTime sortByStartTime = new SortByStartTime();
  private final CalendarEventRepository calendarEventRepository;
  private final UserService userService;
  private final ConversionService conversionService;

  public CalendarEventService(
      CalendarEventRepository calendarEventRepository,
      UserService userService,
      ConversionService conversionService) {
    this.calendarEventRepository = calendarEventRepository;
    this.userService = userService;
    this.conversionService = conversionService;
  }

  public Optional<CalendarEventModel> findById(String id) {
    return calendarEventRepository.findById(id);
  }

  public List<CalendarEventModel> getAllEvents(UserModel userEntity) {
    return calendarEventRepository
        .findByUserEntity(userEntity)
        .stream()
        .sorted(sortByStartTime)
        .toList();
  }

  public List<CalendarEventModel> filterEventsByInterval(
      String interval, Instant startTime) {
    return switch (interval) {
      case "week" -> filterEventsOfWeek(startTime);
      default -> new ArrayList<CalendarEventModel>();
    };
  }

  public CalendarEventModel create(CalendarEventRequest eventRequest) {
    CalendarEventModel newEvent = conversionService.convert(
        Pair.of(eventRequest, userService.getCurrentUser()),
        CalendarEventModel.class);

    if (!isEventInsertable(newEvent)) {
      throw new CalendarEventException("Time conflicted");
    }

    return calendarEventRepository.save(newEvent);
  }

  public CalendarEventModel update(String id, CalendarEventRequest eventRequest) {
    CalendarEventModel eventModel = findById(id)
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

  private boolean isEventInsertable(CalendarEventModel calendarEvent) {
    List<CalendarEventModel> allEvents = getAllEvents(
        calendarEvent.getUserEntity());

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

  private List<CalendarEventModel> filterEventsOfWeek(Instant startTime) {
    // todo: parallel streams
    return Stream
        .concat(calendarEventRepository.filterOneTimeEventsBetween(
            userService.getCurrentUser(), startTime,
            startTime.plus(7, ChronoUnit.DAYS)).stream(),
            new ArrayList<CalendarEventModel>().stream())
        .sorted(sortByStartTime)
        .toList();
  }
}
