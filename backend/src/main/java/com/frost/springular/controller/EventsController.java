package com.frost.springular.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.dto.CalendarEventDto;
import com.frost.springular.dto.CalendarEventDto.Repeat;
import com.frost.springular.dto.CalendarEventReponseDto;
import com.frost.springular.entity.CalendarEventEntity;
import com.frost.springular.entity.UserEntity;
import com.frost.springular.exception.CustomRepeatIntervalException;
import com.frost.springular.repository.CalendarEventRepository;
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
    public ResponseEntity<CalendarEventReponseDto> addEvent(
            @Valid @RequestBody CalendarEventDto event) {
        // todo: define a custom bean validator
        if (event.getRepeat() == Repeat.custom) {
            if (event.getRepeatEvery() == null) {
                throw new CustomRepeatIntervalException();
            }
        } else {
            event.setRepeatEvery(null);
        }

        CalendarEventEntity newEvent = calendarEventRepository.save(
                CalendarEventEntity.builder()
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

        return ResponseEntity.ok(new CalendarEventReponseDto(newEvent));
    }
}
