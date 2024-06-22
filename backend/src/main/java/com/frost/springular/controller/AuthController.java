package com.frost.springular.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.dto.LoginDTO;
import com.frost.springular.dto.SignupDTO;
import com.frost.springular.entity.UserEntity;
import com.frost.springular.exception.DuplicatedEmailException;
import com.frost.springular.response.TokenResponse;
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
    public ResponseEntity<TokenResponse> authenticate(@RequestBody LoginDTO loginInfo) {
        UserEntity authenticatedUser = authService.authenticate(loginInfo);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        return ResponseEntity.ok(new TokenResponse(jwtToken, jwtService.getExpirationTime()));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserEntity> register(@RequestBody SignupDTO signupInfo)
            throws DuplicatedEmailException {
        return ResponseEntity.ok(authService.register(signupInfo));
    }
}
