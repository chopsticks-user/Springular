package com.frost.springular.request;

import com.frost.springular.enumerated.CalendarEventRepeat;
import com.frost.springular.enumerated.CalendarEventRepeatUnit;
import com.frost.springular.validator.CalendarEventRequestConstraint;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CalendarEventRequestConstraint
public class CalendarEventRequest {
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class RepeatEvery {
    private Integer value;
    private CalendarEventRepeatUnit unit;
  }

  private String title;

  private String description;

  private String color;

  private Instant start;

  private int durationMinutes;

  @Builder.Default()
  private CalendarEventRepeat repeat = CalendarEventRepeat.none;

  @Builder.Default()
  private RepeatEvery repeatEvery = null;

}
