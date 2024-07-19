package com.frost.springular.exception;

import org.springframework.http.HttpStatus;

public class CalendarEventException extends HttpServerException {
  public CalendarEventException(String reason) {
    super(HttpStatus.BAD_REQUEST, reason);
  }
}
