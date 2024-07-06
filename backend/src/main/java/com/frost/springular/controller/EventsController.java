package com.frost.springular.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.object.exception.CustomRepeatIntervalException;
import com.frost.springular.object.model.CalendarEventModel;
import com.frost.springular.object.model.UserModel;
import com.frost.springular.object.repository.CalendarEventRepository;
import com.frost.springular.object.request.CalendarEventRequest;
import com.frost.springular.object.request.CalendarEventRequest.Repeat;
import com.frost.springular.object.response.CalendarEventReponse;
import com.frost.springular.service.UserService;

import jakarta.validation.Valid;

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

    @PostMapping({ "", "/" })
    public ResponseEntity<CalendarEventReponse> addEvent(
            @Valid @RequestBody CalendarEventRequest event) {
        // todo: define a custom bean validator
        if (event.getRepeat() == Repeat.custom) {
            if (event.getRepeatEvery() == null) {
                throw new CustomRepeatIntervalException();
            }
        } else {
            event.setRepeatEvery(null);
        }

        CalendarEventModel newEvent = calendarEventRepository.save(
                CalendarEventModel.builder()
                        .title(event.getTitle())
                        .description(event.getDescription())
                        .color(event.getColor())
                        .start(event.getStart())
                        .durationMinutes(event.getDurationMinutes())
                        .repeat(event.getRepeat())
                        .repeatEveryValue(
                                event.getRepeatEvery() == null
                                        ? null
                                        : event.getRepeatEvery().getValue())
                        .repeatEveryUnit(
                                event.getRepeatEvery() == null
                                        ? null
                                        : event.getRepeatEvery().getUnit())
                        .userEntity(userService.getCurrentUser())
                        .build());

        return ResponseEntity.ok(new CalendarEventReponse(newEvent));
    }
}
