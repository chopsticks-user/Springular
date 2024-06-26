package com.frost.springular.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.entity.UserEntity;
import com.frost.springular.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/users")
public class UsersController {
    private final UserService userService;

    public UsersController(UserService authService) {
        this.userService = authService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserEntity> authenticatedUser() {
        var currentUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    // * for testing purposes
    @GetMapping({ "/", "" })
    public ResponseEntity<List<UserEntity>> allUsers() {
        return ResponseEntity.ok(userService.allUsers());
    }
}
