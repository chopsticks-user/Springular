package com.frost.springular.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.model.User;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UsersController {
    @GetMapping("/users")
    public User getUsers(User user) {
        return user;
    }
}
