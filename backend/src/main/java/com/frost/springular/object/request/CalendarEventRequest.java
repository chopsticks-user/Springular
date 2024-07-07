package com.frost.springular.object.request;

import com.frost.springular.object.enumerated.CalendarEventRepeat;
import com.frost.springular.object.enumerated.CalendarEventRepeatUnit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalendarEventRequest {
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class RepeatEvery {
    private Integer value;
    private CalendarEventRepeatUnit unit;
  }

  @NotBlank(message = "Title is required.")
  @Size(max = 50, message = "Title must be at most 50 characters.")
  private String title;

  @Size(max = 200, message = "Description must be at most 200 characters.")
  private String description;

  @NotBlank(message = "Color is required.")
  @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$",
           message = "Invalid color hex code.")
  private String color;

  @NotNull(message = "Start time is required.") private Instant start;

  @Positive(message = "Duration is required.") private int durationMinutes;

  @Builder.Default()
  private CalendarEventRepeat repeat = CalendarEventRepeat.none;

  @Builder.Default() private RepeatEvery repeatEvery = null;
}
