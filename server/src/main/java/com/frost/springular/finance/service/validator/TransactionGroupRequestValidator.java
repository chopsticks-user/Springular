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

    if (request.getPath().isEmpty() || request.getPath().isBlank() ||
        request.getPath().matches(
            "^(/[a-zA-Z][a-zA-Z_-]*[a-zA-Z])*$|^/$")) {
      throw new FinanceException("Invalid path");
    }

    return true;
  }
}
