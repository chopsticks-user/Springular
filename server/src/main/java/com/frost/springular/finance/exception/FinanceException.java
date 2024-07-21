package com.frost.springular.finance.exception;

import org.springframework.http.HttpStatus;

import com.frost.springular.shared.exception.HttpServerException;

public class FinanceException extends HttpServerException {
  public FinanceException(String reason) {
    super(HttpStatus.BAD_REQUEST, reason);
  }
}
