package com.frost.springular.object.validator;

import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

import com.frost.springular.object.constraint.CalendarEventRequestConstraint;
import com.frost.springular.object.enumerated.CalendarEventRepeat;
import com.frost.springular.object.exception.CalendarEventException;
import com.frost.springular.object.request.CalendarEventRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CalendarEventRequestValidator
    implements ConstraintValidator<CalendarEventRequestConstraint, CalendarEventRequest> {
  @Override
  public void initialize(CalendarEventRequestConstraint constraintAnnotation) {

  }

  @Override
  public boolean isValid(
      CalendarEventRequest request, ConstraintValidatorContext context) {
    if (request == null) {
      return true;
    }

    if (request.getTitle().isEmpty() || request.getTitle().isBlank()) {
      throw new CalendarEventException(
          "Title cannot be empty or blank");
    } else if (request.getTitle().length() > 50) {
      throw new CalendarEventException(
          "Title must be at most 50 characters");
    }

    if (request.getDescription().length() > 200) {
      throw new CalendarEventException(
          "Description must be at most 200 characters");
    }

    if (request.getColor().isEmpty() || request.getColor().isBlank()) {
      throw new CalendarEventException(
          "Color cannot be empty or blank");
    } else if (!request.getColor().matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")) {
      throw new CalendarEventException("Invalid color hex code");
    }

    if (request.getStart() == null) {
      throw new CalendarEventException("Start time cannot be null");
    } else if ((request.getStart().truncatedTo(ChronoUnit.MINUTES) != request.getStart())
        || (request.getStart().get(ChronoField.MINUTE_OF_HOUR) % 5 != 0)) {
      throw new CalendarEventException(
          "Minutes of start time must be a multiple of 5");
    }

    if ((request.getDurationMinutes() < 5)
        || (request.getDurationMinutes() % 5 != 0)) {
      throw new CalendarEventException(
          "Duration in minutes must be an integer and a multiple of 5");
    }

    var repeatEvery = request.getRepeatEvery();
    if (request.getRepeat() == CalendarEventRepeat.custom) {

      if (repeatEvery == null || repeatEvery.getValue() == null ||
          repeatEvery.getUnit() == null) {
        throw new CalendarEventException(
            "Custom repeating interval must be specified");
      }
    } else if (repeatEvery != null) {
      throw new CalendarEventException(
          "Unexpected specified custom repeating interval");
    }

    return true;
  }
}
