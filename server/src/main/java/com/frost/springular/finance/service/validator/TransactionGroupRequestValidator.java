package com.frost.springular.finance.service.validator;

import com.frost.springular.finance.data.request.TransactionGroupRequest;
import com.frost.springular.finance.exception.FinanceException;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TransactionGroupRequestValidator
    implements
    ConstraintValidator<TransactionGroupRequestConstraint, TransactionGroupRequest> {
  @Override
  public void initialize(TransactionGroupRequestConstraint constraintAnnotation) {
  }

  @Override
  public boolean isValid(
      TransactionGroupRequest request, ConstraintValidatorContext context) {
    if (request == null) {
      return true;
    }

    if (request.getName().isEmpty() || request.getName().isBlank()) {
      throw new FinanceException("Name cannot be empty or blank");
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
