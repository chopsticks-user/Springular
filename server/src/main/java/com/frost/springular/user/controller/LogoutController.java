package com.frost.springular.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.user.service.RefreshTokenService;
import com.frost.springular.user.service.UserService;

// Todo: /logout
@RestController
@RequestMapping("/api/logout")
public class LogoutController {
  private final RefreshTokenService jwtRefreshTokenService;
  private final UserService userService;

  public LogoutController(
      RefreshTokenService jwtRefreshTokenService,
      UserService userService) {
    this.jwtRefreshTokenService = jwtRefreshTokenService;
    this.userService = userService;
  }

  @GetMapping("")
  public void logout() {
    jwtRefreshTokenService.revokeToken(userService.getCurrentUser());

    // Todo: revoke access token
  }
}
