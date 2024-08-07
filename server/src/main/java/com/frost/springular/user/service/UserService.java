package com.frost.springular.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.frost.springular.user.data.model.UserModel;
import com.frost.springular.user.data.model.UserRepository;

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

  public Optional<UserModel> findUserById(String id) {
    return userRepository.findById(id);
  }

  public Optional<UserModel> findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public UserModel getCurrentUser() {
    return (UserModel) SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();
  }
}
