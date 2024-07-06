package com.frost.springular.dto;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalendarEventDto {
    public static enum Repeat {
        daily, weekly, monthly, yearly, custom, none
    }

    @Data
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

    @NotBlank(message = "Title is required.")
    @Size(max = 50, message = "Title must be at most 50 characters.")
    private String title;

    @Size(max = 200, message = "Description must be at most 200 characters.")
    private String description;

    @NotBlank(message = "Color is required.")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Invalid color hex code.")
    private String color;

    @NotBlank(message = "Start time is required.")
    private Instant start;

    @NotBlank(message = "Duration is required.")
    private int durationMinutes;

    @Builder.Default()
    private Repeat repeat = Repeat.none;

    @Builder.Default()
    private RepeatEvery repeatEvery = null;
}
