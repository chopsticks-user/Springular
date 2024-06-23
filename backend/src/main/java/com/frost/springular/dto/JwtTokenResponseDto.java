package com.frost.springular.dto;

public record JwtTokenResponseDto(
                JwtAccessTokenDTO accessToken,
                JwtRefreshTokenDTO refreshToken) {
}
