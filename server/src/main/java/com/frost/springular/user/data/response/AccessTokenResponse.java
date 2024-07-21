package com.frost.springular.user.data.response;

import java.util.Date;

public record AccessTokenResponse(String token, Date expiresAt) {
}
