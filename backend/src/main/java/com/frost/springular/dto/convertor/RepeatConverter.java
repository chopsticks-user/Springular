package com.frost.springular.dto.convertor;

import com.frost.springular.dto.CalendarEventDto;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter(autoApply = true)
public class RepeatConverter
        implements AttributeConverter<CalendarEventDto.Repeat, String> {
    @Override
    public String convertToDatabaseColumn(CalendarEventDto.Repeat repeat) {
        if (repeat == null) {
            return null;
        }
        return repeat.toString();
    }

    @Override
    public CalendarEventDto.Repeat convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Arrays.stream(CalendarEventDto.Repeat.values())
                .filter(c -> c.toString().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
