package com.frost.springular.object.exception;

public class DuplicatedEmailException extends SecurityException {
  public DuplicatedEmailException() {
    super("Email address already in use");
  }
}
