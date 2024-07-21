package com.frost.springular.user.exception;

public class RefreshTokenExpiredException extends SecurityException {
  public RefreshTokenExpiredException() {
    super("Refresh token expired");
  }
}
