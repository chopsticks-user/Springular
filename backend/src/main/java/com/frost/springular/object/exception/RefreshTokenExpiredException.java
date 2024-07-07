package com.frost.springular.object.exception;

public class RefreshTokenExpiredException extends Exception {
  public RefreshTokenExpiredException() { super("Refresh token expired"); }
}
