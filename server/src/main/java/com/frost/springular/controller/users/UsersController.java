package com.frost.springular.controller.users;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.model.UserModel;
import com.frost.springular.response.UserResponse;
import com.frost.springular.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UsersController {
  private final UserService userService;
  private final ConversionService conversionService;

  public UsersController(
      UserService userService,
      ConversionService conversionService) {
    this.userService = userService;
    this.conversionService = conversionService;
  }

  @GetMapping("/me")
  public ResponseEntity<UserResponse> authenticatedUser() {
    return ResponseEntity.ok(conversionService.convert(
        userService.getCurrentUser(), UserResponse.class));
  }
}
