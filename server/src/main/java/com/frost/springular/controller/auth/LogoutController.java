package com.frost.springular.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.model.UserModel;
import com.frost.springular.service.AccessTokenService;
import com.frost.springular.service.RefreshTokenService;

// Todo: /logout
@RestController
@RequestMapping("/api/logout")
public class LogoutController {
  private final AccessTokenService jwtAccessTokenService;
  private final RefreshTokenService jwtRefreshTokenService;

  public LogoutController(
      AccessTokenService jwtAccessTokenService,
      RefreshTokenService jwtRefreshTokenService) {
    this.jwtAccessTokenService = jwtAccessTokenService;
    this.jwtRefreshTokenService = jwtRefreshTokenService;
  }

  @GetMapping({ "", "/" })
  public void logout() {
    var currentUser = (UserModel) SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();
    jwtRefreshTokenService.revokeToken(currentUser);

    // Todo: revoke access token
  }
}
