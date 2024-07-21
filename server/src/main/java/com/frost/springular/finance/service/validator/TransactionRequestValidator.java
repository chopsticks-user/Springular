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

    if (request.getTime() == null) {
      throw new FinanceException("Transaction time cannot be null");
    }

    if (request.getRevenues() < 0.0 || request.getExpenses() < 0.0) {
      throw new FinanceException(
          "Both revenues and expenses must be positive");
    }

    // if (request.getUserId().isEmpty() || request.getUserId().isBlank()) {
    // throw new FinanceException("user id cannot be empty or blank");
    // }

    return true;
  }
}