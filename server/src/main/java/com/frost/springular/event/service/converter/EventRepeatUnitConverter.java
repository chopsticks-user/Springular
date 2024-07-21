package com.frost.springular.event.service.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

import com.frost.springular.event.data.EventRepeatUnit;

@Converter(autoApply = true)
public class EventRepeatUnitConverter
    implements AttributeConverter<EventRepeatUnit, String> {
  @Override
  public String convertToDatabaseColumn(
      EventRepeatUnit repeat) {
    if (repeat == null) {
      return null;
    }
    return repeat.toString();
  }

  @Override
  public EventRepeatUnit convertToEntityAttribute(
      String code) {
    if (code == null) {
      return null;
    }

    return Arrays.stream(EventRepeatUnit.values())
        .filter(c -> c.toString().equals(code))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
