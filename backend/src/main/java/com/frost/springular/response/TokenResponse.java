package com.frost.springular.response;

public record TokenResponse(String token, long expiresIn) {
}
