package com.frost.springular.dto.convertor;

import com.frost.springular.dto.CalendarEventDto;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter(autoApply = true)
public class UnitConverter
        implements AttributeConverter<CalendarEventDto.RepeatEvery.Unit, String> {
    @Override
    public String convertToDatabaseColumn(
            CalendarEventDto.RepeatEvery.Unit repeat) {
        if (repeat == null) {
            return null;
        }
        return repeat.toString();
    }

    @Override
    public CalendarEventDto.RepeatEvery.Unit convertToEntityAttribute(
            String code) {
        if (code == null) {
            return null;
        }

        return Arrays.stream(CalendarEventDto.RepeatEvery.Unit.values())
                .filter(c -> c.toString().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
