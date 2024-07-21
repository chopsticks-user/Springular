package com.frost.springular.user.controller;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.user.data.response.UserResponse;
import com.frost.springular.user.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;
  private final ConversionService conversionService;

  public UserController(
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

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> authenticatedUser(
      @PathVariable String id) {
    return ResponseEntity.ok(conversionService.convert(
        userService.findUserById(id), UserResponse.class));
  }
}
