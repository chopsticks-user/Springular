package com.frost.springular.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.frost.springular.model.CalendarEventModel;
import com.frost.springular.request.CalendarEventRequest;
import com.frost.springular.response.CalendarEventResponse;
import com.frost.springular.enumerated.CalendarEventRepeat;

@Component
public class CalendarEventsModelToResponseConverter
    implements Converter<CalendarEventModel, CalendarEventResponse> {
  @Override
  public CalendarEventResponse convert(CalendarEventModel model) {
    CalendarEventRepeat repeat = model.getRepeat();

    return CalendarEventResponse.builder()
        .id(model.getId())
        .title(model.getTitle())
        .description(model.getDescription())
        .color(model.getColor())
        .start(model.getStart())
        .durationMinutes(model.getDurationMinutes())
        .repeat(repeat)
        .repeatEvery(repeat == CalendarEventRepeat.custom
            ? new CalendarEventRequest.RepeatEvery(
                model.getRepeatEveryValue(),
                model.getRepeatEveryUnit())
            : null)
        .build();
  }
}
