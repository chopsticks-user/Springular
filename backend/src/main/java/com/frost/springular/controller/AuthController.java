package com.frost.springular.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.config.SecurityConfig;
import com.frost.springular.object.request.RefreshTokenRequest;
import com.frost.springular.object.exception.DuplicatedEmailException;
import com.frost.springular.object.exception.JwtRefreshTokenExpiredException;
import com.frost.springular.object.model.RefreshTokenModel;
import com.frost.springular.object.model.UserModel;
import com.frost.springular.object.request.LoginRequest;
import com.frost.springular.object.request.SignupRequest;
import com.frost.springular.object.response.AccessTokenResponse;
import com.frost.springular.object.response.RefreshTokenResponse;
import com.frost.springular.object.response.TokenResponse;
import com.frost.springular.object.response.UserInfoResponse;
import com.frost.springular.service.AuthService;
import com.frost.springular.service.AccessTokenService;
import com.frost.springular.service.RefreshTokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
        private final AccessTokenService jwtAccessTokenService;
        private final RefreshTokenService jwtRefreshTokenService;
        private final AuthService authService;

        public AuthController(
                        AccessTokenService jwtService,
                        RefreshTokenService jwtRefreshTokenService,
                        AuthService authService) {
                this.jwtAccessTokenService = jwtService;
                this.jwtRefreshTokenService = jwtRefreshTokenService;
                this.authService = authService;
        }

        @PostMapping("/login")
        public ResponseEntity<TokenResponse> authenticate(
                        @Valid @RequestBody LoginRequest loginInfo) {
                UserModel authenticatedUser = authService.authenticate(loginInfo);
                var refreshToken = jwtRefreshTokenService.generateToken(authenticatedUser.getUsername());
                var response = new TokenResponse(
                                jwtAccessTokenService.generateToken(authenticatedUser),
                                new RefreshTokenResponse(refreshToken.getToken(), refreshToken.getExpirationDate()));
                return ResponseEntity.ok(response);
        }

        @PostMapping("/signup")
        public ResponseEntity<UserInfoResponse> register(
                        @Valid @RequestBody SignupRequest signupInfo)
                        throws DuplicatedEmailException {
                return ResponseEntity.ok(
                                new UserInfoResponse(authService.register(signupInfo)));
        }

        @PostMapping("/refresh")
        public ResponseEntity<TokenResponse> refreshAccessToken(
                        @Valid @RequestBody RefreshTokenRequest request)
                        throws JwtRefreshTokenExpiredException {
                RefreshTokenModel refreshToken = jwtRefreshTokenService
                                .findByToken(request.getRefreshToken()).orElse(null);

                if (refreshToken == null) {
                        throw new JwtRefreshTokenExpiredException();
                }

                jwtRefreshTokenService.verifyExpiration(refreshToken);

                // todo: blacklist unused tokens

                return ResponseEntity.ok(new TokenResponse(
                                jwtAccessTokenService.generateToken(refreshToken.getUserEntity()),
                                new RefreshTokenResponse(
                                                refreshToken.getToken(),
                                                refreshToken.getExpirationDate())));
        }
}
