package com.frost.springular.object.response;

import java.time.Instant;

public record RefreshTokenResponse(String token, Instant expiresAt) {
}
