package com.frost.springular.user.data.response;

public record TokenResponse(
    AccessTokenResponse accessToken,
    RefreshTokenResponse refreshToken) {
}
