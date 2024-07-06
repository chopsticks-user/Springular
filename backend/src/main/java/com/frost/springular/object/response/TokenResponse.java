package com.frost.springular.object.response;

public record TokenResponse(
        AccessTokenResponse accessToken,
        RefreshTokenResponse refreshToken) {
}
