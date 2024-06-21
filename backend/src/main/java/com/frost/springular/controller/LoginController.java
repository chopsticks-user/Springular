package com.frost.springular.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.frost.springular.dto.LoginDTO;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    // @PostMapping("/api/login")
    // public TokenDTO getToken(@RequestBody LoginDTO loginInfo) {
    // // TODO: business logic
    // return new TokenDTO(String.format("%s_%s",
    // loginInfo.email(), loginInfo.password()));
    // }

}
