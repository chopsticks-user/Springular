package com.frost.springular.response;

import com.frost.springular.enumerated.CalendarEventRepeat;
import com.frost.springular.model.CalendarEventModel;
import com.frost.springular.request.CalendarEventRequest;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalendarEventResponse {
  private String id;
  private String title;
  private String description;
  private String color;
  private Instant start;
  private int durationMinutes;
  private CalendarEventRepeat repeat;
  private CalendarEventRequest.RepeatEvery repeatEvery;
  private UserResponse owner;
}
