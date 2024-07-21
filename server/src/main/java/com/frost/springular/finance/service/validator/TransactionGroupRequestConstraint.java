package com.frost.springular.finance.service.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = TransactionGroupRequestValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface TransactionGroupRequestConstraint {
  String message() default "Unsatisfied constraints for TransactionGroupRequest";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
