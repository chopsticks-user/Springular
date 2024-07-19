package com.frost.springular.exception;

public class DuplicatedEmailException extends SecurityException {
  public DuplicatedEmailException() {
    super("Email address already in use");
  }
}
