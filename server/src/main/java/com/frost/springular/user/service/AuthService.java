package com.frost.springular.user.service;

import org.springframework.core.convert.ConversionService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.frost.springular.shared.tuple.Pair;
import com.frost.springular.user.data.model.UserModel;
import com.frost.springular.user.data.model.UserRepository;
import com.frost.springular.user.data.request.LoginRequest;
import com.frost.springular.user.data.request.SignupRequest;
import com.frost.springular.user.exception.DuplicatedEmailException;

@Service
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final ConversionService conversionService;

  public AuthService(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager,
      ConversionService conversionService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.conversionService = conversionService;
  }

  public UserModel register(SignupRequest signupRequest)
      throws DuplicatedEmailException {
    if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
      throw new DuplicatedEmailException();
    }

    return userRepository.save(
        conversionService.convert(
            Pair.of(signupRequest,
                passwordEncoder.encode(signupRequest.getPassword())),
            UserModel.class));
  }

  public UserModel authenticate(LoginRequest loginRequest) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getEmail(), loginRequest.getPassword()));
    return userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
  }
}
