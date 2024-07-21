package com.frost.springular.event.data.request;

import java.time.Instant;

import com.frost.springular.event.data.EventRepeat;
import com.frost.springular.event.data.EventRepeatUnit;
import com.frost.springular.event.service.validator.CalendarEventRequestConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CalendarEventRequestConstraint
public class EventRequest {
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class RepeatEvery {
    private Integer value;
    private EventRepeatUnit unit;
  }

  private String title;

  private String description;

  private String color;

  private Instant start;

  private int durationMinutes;

  @Builder.Default()
  private EventRepeat repeat = EventRepeat.none;

  @Builder.Default()
  private RepeatEvery repeatEvery = null;

}
