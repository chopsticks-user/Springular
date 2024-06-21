package com.frost.springular.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.model.LoginModel;
import com.frost.springular.model.TokenModel;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {
    @PostMapping("/api/login")
    public TokenModel getToken(@RequestBody LoginModel loginInfo) {
        // TODO: business logic
        return new TokenModel(String.format("%s_%s",
                loginInfo.email(), loginInfo.password()));
    }
}
