package com.frost.springular.event.service.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;

import com.frost.springular.event.data.EventRepeat;

@Converter(autoApply = true)
public class EventRepeatConverter
    implements AttributeConverter<EventRepeat, String> {
  @Override
  public String convertToDatabaseColumn(EventRepeat repeat) {
    if (repeat == null) {
      return null;
    }
    return repeat.toString();
  }

  @Override
  public EventRepeat convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
    }

    return Arrays.stream(EventRepeat.values())
        .filter(c -> c.toString().equals(code))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
