package com.frost.springular.response;

public record TokenResponse(
    AccessTokenResponse accessToken,
    RefreshTokenResponse refreshToken) {
}
