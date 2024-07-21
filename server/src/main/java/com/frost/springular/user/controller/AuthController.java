package com.frost.springular.user.controller;

import jakarta.validation.Valid;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.user.data.model.RefreshTokenModel;
import com.frost.springular.user.data.model.UserModel;
import com.frost.springular.user.data.request.LoginRequest;
import com.frost.springular.user.data.request.RefreshTokenRequest;
import com.frost.springular.user.data.request.SignupRequest;
import com.frost.springular.user.data.response.RefreshTokenResponse;
import com.frost.springular.user.data.response.TokenResponse;
import com.frost.springular.user.data.response.UserResponse;
import com.frost.springular.user.exception.DuplicatedEmailException;
import com.frost.springular.user.exception.RefreshTokenExpiredException;
import com.frost.springular.user.service.AccessTokenService;
import com.frost.springular.user.service.AuthService;
import com.frost.springular.user.service.RefreshTokenService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AccessTokenService jwtAccessTokenService;
  private final RefreshTokenService jwtRefreshTokenService;
  private final AuthService authService;
  private final ConversionService conversionService;

  public AuthController(
      AccessTokenService jwtService,
      RefreshTokenService jwtRefreshTokenService,
      AuthService authService,
      ConversionService conversionService) {
    this.jwtAccessTokenService = jwtService;
    this.jwtRefreshTokenService = jwtRefreshTokenService;
    this.authService = authService;
    this.conversionService = conversionService;
  }

  @PostMapping("/login")
  public ResponseEntity<TokenResponse> authenticate(@Valid @RequestBody LoginRequest loginInfo) {
    UserModel authenticatedUser = authService.authenticate(loginInfo);
    var refreshToken = jwtRefreshTokenService.generateToken(authenticatedUser.getUsername());
    var response = new TokenResponse(
        jwtAccessTokenService.generateToken(authenticatedUser),
        new RefreshTokenResponse(refreshToken.getToken(),
            refreshToken.getExpirationDate()));
    return ResponseEntity.ok(response);
  }

  @PostMapping("/signup")
  public ResponseEntity<UserResponse> register(
      @Valid @RequestBody SignupRequest signupInfo)
      throws DuplicatedEmailException {
    return ResponseEntity.ok(conversionService.convert(
        authService.register(signupInfo), UserResponse.class));
  }

  @PostMapping("/refresh")
  public ResponseEntity<TokenResponse> refreshAccessToken(@Valid @RequestBody RefreshTokenRequest request)
      throws RefreshTokenExpiredException {
    RefreshTokenModel refreshToken = jwtRefreshTokenService.findByToken(request.getRefreshToken())
        .orElse(null);

    if (refreshToken == null) {
      throw new RefreshTokenExpiredException();
    }

    jwtRefreshTokenService.verifyExpiration(refreshToken);

    // todo: blacklist unused tokens

    return ResponseEntity.ok(new TokenResponse(
        jwtAccessTokenService.generateToken(refreshToken.getUserEntity()),
        new RefreshTokenResponse(refreshToken.getToken(),
            refreshToken.getExpirationDate())));
  }
}
