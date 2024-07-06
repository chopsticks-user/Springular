package com.frost.springular.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.dto.LoginRequestDTO;
import com.frost.springular.config.SecurityConfig;
import com.frost.springular.dto.JwtAccessTokenDTO;
import com.frost.springular.dto.JwtRefreshTokenDTO;
import com.frost.springular.dto.JwtRefreshTokenRequestDto;
import com.frost.springular.dto.JwtTokenResponseDto;
import com.frost.springular.dto.SignupRequestDTO;
import com.frost.springular.entity.JwtRefreshTokenEntity;
import com.frost.springular.entity.UserEntity;
import com.frost.springular.exception.DuplicatedEmailException;
import com.frost.springular.exception.JwtRefreshTokenExpiredException;
import com.frost.springular.service.AuthService;
import com.frost.springular.service.JwtAccessTokenService;
import com.frost.springular.service.JwtRefreshTokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtAccessTokenService jwtAccessTokenService;
    private final JwtRefreshTokenService jwtRefreshTokenService;
    private final AuthService authService;

    public AuthController(
            JwtAccessTokenService jwtService,
            JwtRefreshTokenService jwtRefreshTokenService,
            AuthService authService) {
        this.jwtAccessTokenService = jwtService;
        this.jwtRefreshTokenService = jwtRefreshTokenService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenResponseDto> authenticate(
            @Valid @RequestBody LoginRequestDTO loginInfo) {
        UserEntity authenticatedUser = authService.authenticate(loginInfo);
        var refreshToken = jwtRefreshTokenService.generateToken(authenticatedUser.getUsername());
        var response = new JwtTokenResponseDto(
                jwtAccessTokenService.generateToken(authenticatedUser),
                new JwtRefreshTokenDTO(refreshToken.getToken(), refreshToken.getExpirationDate()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserEntity> register(
            @Valid @RequestBody SignupRequestDTO signupInfo)
            throws DuplicatedEmailException {
        return ResponseEntity.ok(authService.register(signupInfo));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtTokenResponseDto> refreshAccessToken(
            @Valid @RequestBody JwtRefreshTokenRequestDto request)
            throws JwtRefreshTokenExpiredException {
        JwtRefreshTokenEntity refreshToken = jwtRefreshTokenService
                .findByToken(request.getRefreshToken()).orElse(null);

        if (refreshToken == null) {
            throw new JwtRefreshTokenExpiredException();
        }

        jwtRefreshTokenService.verifyExpiration(refreshToken);

        // todo: blacklist unused tokens

        return ResponseEntity.ok(new JwtTokenResponseDto(
                jwtAccessTokenService.generateToken(refreshToken.getUserEntity()),
                new JwtRefreshTokenDTO(
                        refreshToken.getToken(),
                        refreshToken.getExpirationDate())));
    }
}
