package com.frost.springular.user.data.response;

import java.time.Instant;

public record RefreshTokenResponse(String token, Instant expiresAt) {
}
