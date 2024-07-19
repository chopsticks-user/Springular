package com.frost.springular.response;

import java.util.Date;

public record AccessTokenResponse(String token, Date expiresAt) {
}
