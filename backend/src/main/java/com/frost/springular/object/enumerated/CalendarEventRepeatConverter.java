package com.frost.springular.object.enumerated;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

import com.frost.springular.object.request.CalendarEventRequest;

@Converter(autoApply = true)
public class CalendarEventRepeatConverter
        implements AttributeConverter<CalendarEventRequest.Repeat, String> {
    @Override
    public String convertToDatabaseColumn(CalendarEventRequest.Repeat repeat) {
        if (repeat == null) {
            return null;
        }
        return repeat.toString();
    }

    @Override
    public CalendarEventRequest.Repeat convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Arrays.stream(CalendarEventRequest.Repeat.values())
                .filter(c -> c.toString().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
