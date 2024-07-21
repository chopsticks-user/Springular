package com.frost.springular.event.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.frost.springular.event.data.EventRepeat;
import com.frost.springular.event.data.model.EventModel;
import com.frost.springular.event.data.request.EventRequest;
import com.frost.springular.event.data.request.EventRequest.RepeatEvery;
import com.frost.springular.event.data.response.EventResponse;

@Component
public class EventToResponseConverter
    implements Converter<EventModel, EventResponse> {
  @Override
  public EventResponse convert(EventModel model) {
    EventRepeat repeat = model.getRepeat();

    return EventResponse.builder()
        .id(model.getId())
        .title(model.getTitle())
        .description(model.getDescription())
        .color(model.getColor())
        .start(model.getStart())
        .durationMinutes(model.getDurationMinutes())
        .repeat(repeat)
        .repeatEvery(repeat == EventRepeat.custom
            ? new EventRequest.RepeatEvery(
                model.getRepeatEveryValue(),
                model.getRepeatEveryUnit())
            : null)
        .build();
  }
}
