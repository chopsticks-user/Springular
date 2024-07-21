package com.frost.springular.event.data.response;

import java.time.Instant;

import com.frost.springular.event.data.EventRepeat;
import com.frost.springular.event.data.request.EventRequest;
import com.frost.springular.user.data.response.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {
  private String id;
  private String title;
  private String description;
  private String color;
  private Instant start;
  private int durationMinutes;
  private EventRepeat repeat;
  private EventRequest.RepeatEvery repeatEvery;
  private UserResponse owner;
}
