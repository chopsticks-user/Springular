package com.frost.springular.controller;

import com.frost.springular.object.enumerated.CalendarEventRepeat;
import com.frost.springular.object.exception.CustomRepeatIntervalException;
import com.frost.springular.object.model.CalendarEventModel;
import com.frost.springular.object.request.CalendarEventRequest;
import com.frost.springular.object.response.CalendarEventReponse;
import com.frost.springular.service.CalendarEventService;
import com.frost.springular.service.UserService;
import jakarta.validation.Valid;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
  private final UserService userService;
  private final CalendarEventService calendarEventService;

  public EventsController(UserService userService,
      CalendarEventService calendarEventService) {
    this.userService = userService;
    this.calendarEventService = calendarEventService;
  }

  @GetMapping("")
  public ResponseEntity<List<CalendarEventReponse>> filterCalendarEvents(
      @RequestParam String interval, @RequestParam Instant start) {
    var result = new ArrayList<CalendarEventReponse>();

    calendarEventService
        .filter(userService.getCurrentUser(), interval,
            start.truncatedTo(ChronoUnit.DAYS))
        .forEach((CalendarEventModel model) -> {
          result.add(new CalendarEventReponse(model));
        });

    return ResponseEntity.ok(result);
  }

  @PostMapping("")
  public ResponseEntity<CalendarEventReponse> addCalendarEvent(
      @Valid @RequestBody CalendarEventRequest calendarEvent) {
    // todo: define a custom bean validator
    validateRepeat(calendarEvent);

    CalendarEventModel newEvent = CalendarEventModel.builder()
        .title(calendarEvent.getTitle())
        .description(calendarEvent.getDescription())
        .color(calendarEvent.getColor())
        .start(calendarEvent.getStart())
        .durationMinutes(calendarEvent.getDurationMinutes())
        .repeat(calendarEvent.getRepeat())
        .repeatEveryValue(calendarEvent.getRepeatEvery().getValue())
        .repeatEveryUnit(calendarEvent.getRepeatEvery().getUnit())
        .userEntity(userService.getCurrentUser())
        .build();

    if (calendarEventService.isEventInsertable(newEvent)) {
      throw new RuntimeException("POST /api/events");
    }

    return ResponseEntity.ok(
        new CalendarEventReponse(calendarEventService.update(newEvent)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<CalendarEventReponse> editCalendarEvent(
      @PathVariable String id,
      @Valid @RequestBody CalendarEventRequest calendarEvent) {
    validateRepeat(calendarEvent);

    // todo: implement an exception class
    CalendarEventModel existedEvent = calendarEventService
        .findById(id)
        .orElseThrow(() -> new RuntimeException("PUT /events/{id}"));

    existedEvent.setTitle(calendarEvent.getTitle());
    existedEvent.setDescription(calendarEvent.getDescription());
    existedEvent.setColor(calendarEvent.getColor());
    existedEvent.setStart(calendarEvent.getStart());
    existedEvent.setDurationMinutes(calendarEvent.getDurationMinutes());
    existedEvent.setRepeat(calendarEvent.getRepeat());
    existedEvent.setRepeatEveryValue(calendarEvent.getRepeatEvery().getValue());
    existedEvent.setRepeatEveryUnit(calendarEvent.getRepeatEvery().getUnit());

    // todo: CalendarEventRequest should also contain userEntity
    calendarEventService.isEventInsertable(existedEvent);

    return ResponseEntity.ok(
        new CalendarEventReponse(calendarEventService.update(existedEvent)));
  }

  @DeleteMapping("/{id}")
  public void deleteCalendarEvent(@PathVariable String id) {
    calendarEventService.deleteById(id);
  }

  private static void validateRepeat(CalendarEventRequest calendarEvent) {
    if (calendarEvent.getRepeat() == CalendarEventRepeat.custom) {
      var repeatEvery = calendarEvent.getRepeatEvery();

      if (repeatEvery == null || repeatEvery.getValue() == null ||
          repeatEvery.getUnit() == null) {
        throw new CustomRepeatIntervalException();
      }
    } else {
      calendarEvent.setRepeatEvery(
          new CalendarEventRequest.RepeatEvery(null, null));
    }
  }
}
