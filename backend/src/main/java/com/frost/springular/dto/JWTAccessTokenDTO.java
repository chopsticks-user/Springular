package com.frost.springular.dto;

import java.util.Date;

public record JWTAccessTokenDTO(String token, Date expiresAt) {
}
