package com.frost.springular.dto;

public record JwtTokenResponseDto(
        JwtAccessTokenDTO accessTokenDto,
        JwtRefreshTokenDTO refreshTokenDto) {
}
