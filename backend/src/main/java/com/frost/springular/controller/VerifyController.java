package com.frost.springular.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.dto.JwtRefreshTokenRequestDto;
import com.frost.springular.entity.JwtRefreshTokenEntity;
import com.frost.springular.entity.UserEntity;
import com.frost.springular.exception.JwtRefreshTokenExpiredException;
import com.frost.springular.service.JwtRefreshTokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/verify")
public class VerifyController {
    private final JwtRefreshTokenService jwtRefreshTokenService;

    public VerifyController(JwtRefreshTokenService jwtRefreshTokenService) {
        this.jwtRefreshTokenService = jwtRefreshTokenService;
    }

    @PostMapping({ "", "/" })
    public void verify(@Valid @RequestBody JwtRefreshTokenRequestDto request)
            throws JwtRefreshTokenExpiredException {
        var currentUser = (UserEntity) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        JwtRefreshTokenEntity currentUserRefreshToken = jwtRefreshTokenService
                .findByToken(request.getRefreshToken())
                // user already logged out
                .orElseThrow(() -> new JwtRefreshTokenExpiredException());

        // refresh token expired
        jwtRefreshTokenService.verifyExpiration(currentUserRefreshToken);

        // access token and refresh token mismatch
        if (!currentUser.equals(currentUserRefreshToken.getUserEntity())) {
            throw new JwtRefreshTokenExpiredException();
        }
    }
}
