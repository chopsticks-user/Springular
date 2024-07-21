package com.frost.springular.event.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.frost.springular.event.data.model.EventModel;
import com.frost.springular.event.data.request.EventRequest;
import com.frost.springular.shared.tuple.Pair;
import com.frost.springular.user.data.model.UserModel;

@Component
public class EventToModelConverter implements
    Converter<Pair<EventRequest, UserModel>, EventModel> {
  @Override
  public EventModel convert(Pair<EventRequest, UserModel> pair) {
    return EventModel.builder()
        .title(pair.getFirst().getTitle())
        .description(pair.getFirst().getDescription())
        .color(pair.getFirst().getColor())
        .start(pair.getFirst().getStart())
        .durationMinutes(pair.getFirst().getDurationMinutes())
        .repeat(pair.getFirst().getRepeat())
        .repeatEveryValue(pair.getFirst().getRepeatEvery().getValue())
        .repeatEveryUnit(pair.getFirst().getRepeatEvery().getUnit())
        .user(pair.getSecond())
        .build();
  }
}
