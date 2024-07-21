package com.frost.springular.event.exception;

import org.springframework.http.HttpStatus;

import com.frost.springular.shared.exception.HttpServerException;

public class CalendarEventException extends HttpServerException {
  public CalendarEventException(String reason) {
    super(HttpStatus.BAD_REQUEST, reason);
  }
}
