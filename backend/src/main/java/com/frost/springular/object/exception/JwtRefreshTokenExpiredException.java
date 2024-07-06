package com.frost.springular.object.exception;

public class JwtRefreshTokenExpiredException extends Exception {
    public JwtRefreshTokenExpiredException() {
        super("Refresh token expired");
    }
}
