package com.frost.springular.controller;

import com.frost.springular.exception.RefreshTokenExpiredException;
import com.frost.springular.model.RefreshTokenModel;
import com.frost.springular.model.UserModel;
import com.frost.springular.request.RefreshTokenRequest;
import com.frost.springular.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/verify")
public class VerifyController {
  private final RefreshTokenService jwtRefreshTokenService;

  public VerifyController(RefreshTokenService jwtRefreshTokenService) {
    this.jwtRefreshTokenService = jwtRefreshTokenService;
  }

  @PostMapping({ "", "/" })
  public void verify(@Valid @RequestBody RefreshTokenRequest request)
      throws RefreshTokenExpiredException {
    // todo: extract user from header
    var currentUser = (UserModel) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();

    RefreshTokenModel currentUserRefreshToken = jwtRefreshTokenService
        .findByToken(request.getRefreshToken())
        // user already logged out
        .orElseThrow(() -> new RefreshTokenExpiredException());

    // refresh token expired
    jwtRefreshTokenService.verifyExpiration(currentUserRefreshToken);

    // access token and refresh token mismatch
    if (!currentUser.equals(currentUserRefreshToken.getUserEntity())) {
      throw new RefreshTokenExpiredException();
    }
  }
}
