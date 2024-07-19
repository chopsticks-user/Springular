package com.frost.springular.enumerated;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;

@Converter(autoApply = true)
public class CalendarEventRepeatConverter
    implements AttributeConverter<CalendarEventRepeat, String> {
  @Override
  public String convertToDatabaseColumn(CalendarEventRepeat repeat) {
    if (repeat == null) {
      return null;
    }
    return repeat.toString();
  }

  @Override
  public CalendarEventRepeat convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
    }

    return Arrays.stream(CalendarEventRepeat.values())
        .filter(c -> c.toString().equals(code))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
