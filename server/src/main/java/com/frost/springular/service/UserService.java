package com.frost.springular.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.frost.springular.object.model.UserModel;
import com.frost.springular.object.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // * for testing purposes
    public List<UserModel> allUsers() {
        var users = new ArrayList<UserModel>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public UserModel getCurrentUser() {
        // todo: null exception
        return (UserModel) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
