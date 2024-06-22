package com.frost.springular.exception;

public class JwtRefreshTokenExpiredException extends Exception {
    public JwtRefreshTokenExpiredException() {
        super("Refresh token expired");
    }
}
