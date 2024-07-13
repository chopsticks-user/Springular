package com.frost.springular.object.exception;

public class RefreshTokenExpiredException extends SecurityException {
  public RefreshTokenExpiredException() {
    super("Refresh token expired");
  }
}
