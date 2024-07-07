package com.frost.springular.service;

import com.frost.springular.object.enumerated.CalendarEventRepeat;
import com.frost.springular.object.exception.CustomRepeatIntervalException;
import com.frost.springular.object.model.CalendarEventModel;
import com.frost.springular.object.repository.CalendarEventRepository;
import com.frost.springular.object.request.CalendarEventRequest;
import com.frost.springular.object.response.CalendarEventReponse;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CalendarEventService {
  private final CalendarEventRepository calendarEventRepository;

  public CalendarEventService(CalendarEventRepository calendarEventRepository) {
    this.calendarEventRepository = calendarEventRepository;
  }

  public Optional<CalendarEventModel> findById(String id) {
    return calendarEventRepository.findById(id);
  }

  public void deleteById(String id) { calendarEventRepository.deleteById(id); }

  public CalendarEventModel update(CalendarEventModel calendarEvent) {
    return calendarEventRepository.save(calendarEvent);
  }
}
