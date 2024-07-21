package com.frost.springular.event.controller;

import jakarta.validation.Valid;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.event.data.model.EventModel;
import com.frost.springular.event.data.request.EventRequest;
import com.frost.springular.event.data.response.EventResponse;
import com.frost.springular.event.exception.CalendarEventException;
import com.frost.springular.event.service.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {
  private final EventService calendarEventService;
  private final ConversionService conversionService;

  public EventController(
      EventService calendarEventService,
      ConversionService conversionService) {
    this.calendarEventService = calendarEventService;
    this.conversionService = conversionService;
  }

  @GetMapping("")
  public ResponseEntity<List<EventResponse>> filterCalendarEvents(
      @RequestParam String interval, @RequestParam Instant start) {
    return ResponseEntity.ok(
        calendarEventService
            .filterEventsByInterval(interval, start.truncatedTo(ChronoUnit.DAYS))
            .stream()

            .map((EventModel eventModel) -> conversionService.convert(
                eventModel, EventResponse.class))
            .toList());
  }

  @PostMapping("")
  public ResponseEntity<EventResponse> addCalendarEvent(
      @Valid @RequestBody EventRequest eventRequest) {
    return ResponseEntity.ok(conversionService.convert(
        calendarEventService.create(eventRequest),
        EventResponse.class));
  }

  @GetMapping("/{id}")
  public ResponseEntity<EventResponse> getCalendarEvent(
      @PathVariable String id) {
    return ResponseEntity.ok(conversionService.convert(
        calendarEventService.findById(id).orElseThrow(
            () -> new CalendarEventException("Calendar event not found")),
        EventResponse.class));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<EventResponse> editCalendarEvent(
      @PathVariable String id,
      @Valid @RequestBody EventRequest eventRequest) {
    return ResponseEntity.ok(conversionService.convert(
        calendarEventService.update(id, eventRequest),
        EventResponse.class));
  }

  @DeleteMapping("/{id}")
  public void deleteCalendarEvent(@PathVariable String id) {
    calendarEventService.delete(id);
  }
}
