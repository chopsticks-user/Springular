package com.frost.springular.object.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = TransactionRequestValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface TransactionRequestConstraint {
  String message() default "Unsatisfied constraints for TransactionRequest";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
