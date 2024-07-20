package com.frost.springular.exception;

import org.springframework.http.HttpStatus;

public class FinanceException extends HttpServerException {
  public FinanceException(String reason) {
    super(HttpStatus.BAD_REQUEST, reason);
  }
}
