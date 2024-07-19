package com.frost.springular.object.validator;

import com.frost.springular.object.exception.FinanceException;
import com.frost.springular.object.request.TransactionRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TransactionRequestValidator
    implements
    ConstraintValidator<TransactionRequestConstraint, TransactionRequest> {
  @Override
  public void initialize(TransactionRequestConstraint constraintAnnotation) {
  }

  @Override
  public boolean isValid(
      TransactionRequest request, ConstraintValidatorContext context) {
    if (request == null) {
      return true;
    }

    if (request.getTime() == null) {
      throw new FinanceException("Transaction time cannot be null");
    }

    if (request.getRevenues() < 0.0 || request.getExpenses() < 0.0) {
      throw new FinanceException(
          "Both revenues and expenses must be positive");
    }

    if (request.getUserId().isEmpty() || request.getUserId().isBlank()) {
      throw new FinanceException("user id cannot be empty or blank");
    }

    return true;
  }
}
