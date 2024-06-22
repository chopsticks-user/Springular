package com.frost.springular.dto;

import java.util.Date;

public record JwtAccessTokenDTO(String token, Date expiresAt) {
}
