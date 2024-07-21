package com.frost.springular.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

public class HttpServerException extends ResponseStatusException {
  public HttpServerException(
      HttpStatus status, @Nullable String reason) {
    super(status.value(), reason, null);
  }
}
