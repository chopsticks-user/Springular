package com.frost.springular.response;

import java.time.Instant;

public record RefreshTokenResponse(String token, Instant expiresAt) {
}
