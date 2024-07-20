package com.frost.springular.controller.auth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.model.UserModel;
import com.frost.springular.service.AccessTokenService;
import com.frost.springular.service.RefreshTokenService;
import com.frost.springular.service.UserService;

// Todo: /logout
@RestController
@RequestMapping("/api/logout")
public class LogoutController {
  private final AccessTokenService jwtAccessTokenService;
  private final RefreshTokenService jwtRefreshTokenService;
  private final UserService userService;

  public LogoutController(
      AccessTokenService jwtAccessTokenService,
      RefreshTokenService jwtRefreshTokenService,
      UserService userService) {
    this.jwtAccessTokenService = jwtAccessTokenService;
    this.jwtRefreshTokenService = jwtRefreshTokenService;
    this.userService = userService;
  }

  @GetMapping("")
  public void logout() {
    jwtRefreshTokenService.revokeToken(userService.getCurrentUser());

    // Todo: revoke access token
  }
}
