package com.frost.springular.dto;

import java.time.Instant;

public record JwtRefreshTokenDTO(String token, Instant expiresAt) {
}
