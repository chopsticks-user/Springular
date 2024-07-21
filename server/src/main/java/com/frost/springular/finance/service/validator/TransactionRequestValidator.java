package com.frost.springular.finance.service.validator;

import com.frost.springular.finance.data.request.TransactionRequest;
import com.frost.springular.finance.exception.FinanceException;

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

    if (request.getGroupPath().isEmpty() || request.getGroupPath().isBlank() ||
        request.getGroupPath().matches(
            "^(/[a-zA-Z][a-zA-Z_-]*[a-zA-Z])*$|^/$")) {
      throw new FinanceException("Invalid path");
    }

    if (request.getTime() == null) {
      throw new FinanceException("Transaction time cannot be null");
    }

    if (request.getRevenues() < 0.0 || request.getExpenses() < 0.0) {
      throw new FinanceException(
          "Both revenues and expenses must be positive");
    }

    return true;
  }
}
