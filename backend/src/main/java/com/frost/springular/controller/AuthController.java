package com.frost.springular.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.dto.LoginRequestDTO;
import com.frost.springular.dto.JWTAccessTokenDTO;
import com.frost.springular.dto.SignupRequestDTO;
import com.frost.springular.entity.UserEntity;
import com.frost.springular.exception.DuplicatedEmailException;
import com.frost.springular.service.AuthService;
import com.frost.springular.service.JWTService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
public class AuthController {
    private final JWTService jwtService;
    private final AuthService authService;

    public AuthController(JWTService jwtService, AuthService authService) {
        this.jwtService = jwtService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JWTAccessTokenDTO> authenticate(@RequestBody LoginRequestDTO loginInfo) {
        UserEntity authenticatedUser = authService.authenticate(loginInfo);
        return ResponseEntity.ok(jwtService.generateToken(authenticatedUser));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserEntity> register(@RequestBody SignupRequestDTO signupInfo)
            throws DuplicatedEmailException {
        return ResponseEntity.ok(authService.register(signupInfo));
    }
}
