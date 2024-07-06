package com.frost.springular.dto;

import java.time.Instant;

import com.frost.springular.dto.CalendarEventDto.Repeat;
import com.frost.springular.dto.CalendarEventDto.RepeatEvery;
import com.frost.springular.dto.CalendarEventDto.RepeatEvery.Unit;
import com.frost.springular.entity.CalendarEventEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalendarEventReponseDto {
    private String id;
    private String title;
    private String description;
    private String color;
    private Instant start;
    private int durationMinutes;
    private Repeat repeat;
    private RepeatEvery repeatEvery;
    private UserInfoResponseDto userInfoResponseDto;

    public CalendarEventReponseDto(CalendarEventEntity calendarEventEntity) {
        id = calendarEventEntity.getId();
        title = calendarEventEntity.getTitle();
        description = calendarEventEntity.getDescription();
        color = calendarEventEntity.getColor();
        start = calendarEventEntity.getStart();
        durationMinutes = calendarEventEntity.getDurationMinutes();
        repeat = calendarEventEntity.getRepeat();
        repeatEvery = repeat == Repeat.custom
                ? new RepeatEvery(
                        calendarEventEntity.getRepeatEveryValue(),
                        calendarEventEntity.getRepeatEveryUnit())
                : null;
        userInfoResponseDto = new UserInfoResponseDto(
                calendarEventEntity.getUserEntity());
    }
}
