package com.frost.springular.event.service.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = CalendarEventRequestValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CalendarEventRequestConstraint {
  String message() default "Unsatisfied constraints for CalendarEventRequest";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
