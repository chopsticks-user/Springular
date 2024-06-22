package com.frost.springular.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.entity.UserEntity;
import com.frost.springular.service.JwtAccessTokenService;
import com.frost.springular.service.JwtRefreshTokenService;

// Todo: /logout
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/logout")
public class LogoutController {
    private final JwtAccessTokenService jwtAccessTokenService;
    private final JwtRefreshTokenService jwtRefreshTokenService;

    public LogoutController(
            JwtAccessTokenService jwtAccessTokenService,
            JwtRefreshTokenService jwtRefreshTokenService) {
        this.jwtAccessTokenService = jwtAccessTokenService;
        this.jwtRefreshTokenService = jwtRefreshTokenService;
    }

    @GetMapping({ "", "/" })
    public void logout() {
        var currentUser = (UserEntity) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        jwtRefreshTokenService.revokeToken(currentUser);

        // Todo: revoke access token
    }
}
