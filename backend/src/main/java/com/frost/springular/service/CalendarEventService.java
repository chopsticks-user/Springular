package com.frost.springular.service;

import com.frost.springular.object.model.CalendarEventModel;
import com.frost.springular.object.repository.CalendarEventRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CalendarEventService {
  private static class SortByStartTime
      implements Comparator<CalendarEventModel> {
    public int compare(CalendarEventModel a, CalendarEventModel b) {
      return a.getStart().compareTo(b.getStart());
    }
  }

  private final CalendarEventRepository calendarEventRepository;

  public CalendarEventService(CalendarEventRepository calendarEventRepository) {
    this.calendarEventRepository = calendarEventRepository;
  }

  public Optional<CalendarEventModel> findById(String id) {
    return calendarEventRepository.findById(id);
  }

  public void deleteById(String id) { calendarEventRepository.deleteById(id); }

  public List<CalendarEventModel> filter(String interval, Instant startTime) {
    List<CalendarEventModel> result = null;

    switch (interval) {
    case "week": {
      result = calendarEventRepository.filterOneTimeEventsBetween(
          startTime, startTime.plus(7, ChronoUnit.DAYS));
      result.sort(new SortByStartTime());
      break;
    }
    default: {
      result = new ArrayList<CalendarEventModel>();
      break;
    }
    };

    return result;
  }

  public CalendarEventModel update(CalendarEventModel calendarEvent) {
    return calendarEventRepository.save(calendarEvent);
  }
}
