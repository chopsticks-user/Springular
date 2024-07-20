package com.frost.springular.exception;

public class RefreshTokenExpiredException extends SecurityException {
  public RefreshTokenExpiredException() {
    super("Refresh token expired");
  }
}
