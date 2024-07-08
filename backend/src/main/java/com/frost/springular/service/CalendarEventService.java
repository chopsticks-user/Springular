package com.frost.springular.service;

import com.frost.springular.object.model.CalendarEventModel;
import com.frost.springular.object.model.UserModel;
import com.frost.springular.object.repository.CalendarEventRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
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

  private final CalendarEventRepository calendarEventRepository;
  private final SortByStartTime sortByStartTime = new SortByStartTime();

  public CalendarEventService(CalendarEventRepository calendarEventRepository) {
    this.calendarEventRepository = calendarEventRepository;
  }

  public Optional<CalendarEventModel> findById(String id) {
    return calendarEventRepository.findById(id);
  }

  public void deleteById(String id) {
    calendarEventRepository.deleteById(id);
  }

  public CalendarEventModel update(CalendarEventModel calendarEvent) {
    return calendarEventRepository.save(calendarEvent);
  }

  public boolean isEventInsertable(CalendarEventModel calendarEvent) {
    List<CalendarEventModel> allEvents = all(calendarEvent.getUserEntity());

    int index = Collections.binarySearch(
        allEvents, calendarEvent, sortByStartTime),
        lowerIndex = index, upperIndex = 0;

    if (index < 0) {
      int insertionPoint = -index - 1;

      if (insertionPoint > 0 && insertionPoint < allEvents.size()) {
        lowerIndex = insertionPoint - 1;
        upperIndex = insertionPoint;
      }

      return true;
    }

    Instant lowerBoundEndTime = allEvents
        .get(lowerIndex)
        .getStart()
        .plus(allEvents.get(lowerIndex).getDurationMinutes(), ChronoUnit.MINUTES);
    Instant eventEndTime = calendarEvent
        .getStart()
        .plus(calendarEvent.getDurationMinutes(), ChronoUnit.MINUTES);

    return calendarEvent.getStart().compareTo(lowerBoundEndTime) >= 0
        && eventEndTime.compareTo(allEvents.get(upperIndex).getStart()) <= 0;
  }

  public List<CalendarEventModel> filter(UserModel userEntity, String interval,
      Instant startTime) {
    return switch (interval) {
      case "week" -> filterEventsOfWeek(userEntity, startTime);
      default -> new ArrayList<CalendarEventModel>();
    };
  }

  public List<CalendarEventModel> all(UserModel userEntity) {
    return calendarEventRepository
        .findByUserEntity(userEntity)
        .stream()
        .sorted(sortByStartTime)
        .toList();
  }

  private List<CalendarEventModel> filterEventsOfWeek(UserModel userEntity,
      Instant startTime) {
    var oneTimeEvents = calendarEventRepository.filterOneTimeEventsBetween(
        userEntity, startTime, startTime.plus(7, ChronoUnit.DAYS));

    // todo: parallel streams
    return Stream
        .concat(oneTimeEvents.stream(),
            new ArrayList<CalendarEventModel>().stream())
        .sorted(sortByStartTime)
        .toList();
  }
}
