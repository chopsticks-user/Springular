package com.frost.springular.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.frost.springular.model.CalendarEventModel;
import com.frost.springular.model.UserModel;
import com.frost.springular.request.CalendarEventRequest;
import com.frost.springular.util.Pair;

@Component
public class CalendarEventToModelConverter implements
    Converter<Pair<CalendarEventRequest, UserModel>, CalendarEventModel> {
  @Override
  public CalendarEventModel convert(Pair<CalendarEventRequest, UserModel> pair) {
    return CalendarEventModel.builder()
        .title(pair.getFirst().getTitle())
        .description(pair.getFirst().getDescription())
        .color(pair.getFirst().getColor())
        .start(pair.getFirst().getStart())
        .durationMinutes(pair.getFirst().getDurationMinutes())
        .repeat(pair.getFirst().getRepeat())
        .repeatEveryValue(pair.getFirst().getRepeatEvery().getValue())
        .repeatEveryUnit(pair.getFirst().getRepeatEvery().getUnit())
        .userEntity(pair.getSecond())
        .build();
  }
}
