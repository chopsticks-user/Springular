package com.frost.springular.controller.events;

import com.frost.springular.exception.CalendarEventException;
import com.frost.springular.model.CalendarEventModel;
import com.frost.springular.request.CalendarEventRequest;
import com.frost.springular.response.CalendarEventResponse;
import com.frost.springular.service.CalendarEventService;
import com.frost.springular.service.UserService;
import jakarta.validation.Valid;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventsController {
  private final CalendarEventService calendarEventService;
  private final ConversionService conversionService;

  public EventsController(
      CalendarEventService calendarEventService,
      ConversionService conversionService) {
    this.calendarEventService = calendarEventService;
    this.conversionService = conversionService;
  }

  @GetMapping("")
  public ResponseEntity<List<CalendarEventResponse>> filterCalendarEvents(
      @RequestParam String interval, @RequestParam Instant start) {
    return ResponseEntity.ok(
        calendarEventService
            .filterEventsByInterval(interval, start.truncatedTo(ChronoUnit.DAYS))
            .stream()
            .map((CalendarEventModel eventModel) -> conversionService.convert(
                eventModel, CalendarEventResponse.class))
            .toList());
  }

  @PostMapping("")
  public ResponseEntity<CalendarEventResponse> addCalendarEvent(
      @Valid @RequestBody CalendarEventRequest eventRequest) {
    return ResponseEntity.ok(conversionService.convert(
        calendarEventService.create(eventRequest),
        CalendarEventResponse.class));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<CalendarEventResponse> editCalendarEvent(
      @PathVariable String id,
      @Valid @RequestBody CalendarEventRequest eventRequest) {
    return ResponseEntity.ok(conversionService.convert(
        calendarEventService.update(id, eventRequest),
        CalendarEventResponse.class));
  }

  @DeleteMapping("/{id}")
  public void deleteCalendarEvent(@PathVariable String id) {
    calendarEventService.delete(id);
  }
}
