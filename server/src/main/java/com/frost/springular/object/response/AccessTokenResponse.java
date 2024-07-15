package com.frost.springular.object.response;

import java.util.Date;

public record AccessTokenResponse(String token, Date expiresAt) {
}
