package com.frost.springular.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.dto.CalendarEventDto;
import com.frost.springular.entity.CalendarEventEntity;
import com.frost.springular.entity.UserEntity;
import com.frost.springular.repository.CalendarEventRepository;
import com.frost.springular.service.UserService;

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
    public ResponseEntity<CalendarEventEntity> addEvent(
            @RequestBody CalendarEventDto event) {
        CalendarEventEntity newEvent = calendarEventRepository.save(
                CalendarEventEntity.builder()
                        .title(event.getTitle())
                        .description(event.getDescription())
                        .color(event.getColor())
                        .start(event.getStart())
                        .durationMinutes(event.getDurationMinutes())
                        .repeat(event.getRepeat())
                        .repeatEveryValue(event.getRepeatEvery().getValue())
                        .repeatEveryUnit(event.getRepeatEvery().getUnit())
                        .userEntity(userService.getCurrentUser())
                        .build());
        return ResponseEntity.ok(newEvent);
    }
}
