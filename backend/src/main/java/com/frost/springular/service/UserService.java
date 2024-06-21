package com.frost.springular.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.frost.springular.entity.UserEntity;
import com.frost.springular.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // * for testing purposes
    public List<UserEntity> allUsers() {
        var users = new ArrayList<UserEntity>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
}
