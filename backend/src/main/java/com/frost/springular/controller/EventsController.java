package com.frost.springular.controller;

import com.frost.springular.object.enumerated.CalendarEventRepeat;
import com.frost.springular.object.exception.CustomRepeatIntervalException;
import com.frost.springular.object.model.CalendarEventModel;
import com.frost.springular.object.model.UserModel;
import com.frost.springular.object.repository.CalendarEventRepository;
import com.frost.springular.object.request.CalendarEventRequest;
import com.frost.springular.object.response.CalendarEventReponse;
import com.frost.springular.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventsController {
  private final UserService userService;
  private final CalendarEventRepository calendarEventRepository;

  public EventsController(UserService userService,
                          CalendarEventRepository calendarEventRepository) {
    this.userService = userService;
    this.calendarEventRepository = calendarEventRepository;
  }

  @PostMapping("")
  public ResponseEntity<CalendarEventReponse>
  addCalendarEvent(@Valid @RequestBody CalendarEventRequest calendarEvent) {
    // todo: define a custom bean validator
    validateRepeat(calendarEvent);

    CalendarEventModel newEvent =
        CalendarEventModel.builder()
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

    return ResponseEntity.ok(
        new CalendarEventReponse(calendarEventRepository.save(newEvent)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<CalendarEventReponse>
  editCalendarEvent(@PathVariable String id,
                    @Valid @RequestBody CalendarEventRequest calendarEvent) {
    // todo: implement an exception class
    CalendarEventModel existedEvent =
        calendarEventRepository.findById(id).orElseThrow(
            () -> new RuntimeException("PUT /events/{id}"));

    validateRepeat(calendarEvent);

    existedEvent.setTitle(calendarEvent.getTitle());
    existedEvent.setDescription(calendarEvent.getDescription());
    existedEvent.setColor(calendarEvent.getColor());
    existedEvent.setStart(calendarEvent.getStart());
    existedEvent.setRepeat(calendarEvent.getRepeat());
    existedEvent.setRepeatEveryValue(calendarEvent.getRepeatEvery().getValue());
    existedEvent.setRepeatEveryUnit(calendarEvent.getRepeatEvery().getUnit());

    // todo: CalendarEventRequest should also contain userEntity

    return ResponseEntity.ok(
        new CalendarEventReponse(calendarEventRepository.save(existedEvent)));
  }

  @DeleteMapping("/{id}")
  public void deleteCalendarEvent(@PathVariable String id) {
    calendarEventRepository.deleteById(id);
  }

  private static void validateRepeat(CalendarEventRequest calendarEvent) {
    if (calendarEvent.getRepeat() == CalendarEventRepeat.custom) {
      var repeatEvery = calendarEvent.getRepeatEvery();

      if (repeatEvery == null || repeatEvery.getValue() == null ||
          repeatEvery.getUnit() == null) {
        throw new CustomRepeatIntervalException();
      }
    } else {
      calendarEvent.setRepeatEvery(null);
    }
  }
}
