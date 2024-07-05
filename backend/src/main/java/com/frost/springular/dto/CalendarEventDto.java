package com.frost.springular.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalendarEventDto {
    public static enum Repeat {
        daily, weekly, monthly, yearly, custom, none
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RepeatEvery {
        public static enum Unit {
            days, weeks, months, years
        }

        private int value;
        private Unit unit;
    }

    private String id;
    private String title;
    private String description;
    private String color;
    private Instant start;
    private int durationMinutes;
    private Repeat repeat;
    private RepeatEvery repeatEvery;
    private String userId;
}
