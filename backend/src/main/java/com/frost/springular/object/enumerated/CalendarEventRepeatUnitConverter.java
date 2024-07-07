package com.frost.springular.object.enumerated;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter(autoApply = true)
public class CalendarEventRepeatUnitConverter
        implements AttributeConverter<CalendarEventRepeatUnit, String> {
    @Override
    public String convertToDatabaseColumn(
            CalendarEventRepeatUnit repeat) {
        if (repeat == null) {
            return null;
        }
        return repeat.toString();
    }

    @Override
    public CalendarEventRepeatUnit convertToEntityAttribute(
            String code) {
        if (code == null) {
            return null;
        }

        return Arrays.stream(CalendarEventRepeatUnit.values())
                .filter(c -> c.toString().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
