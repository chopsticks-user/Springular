package com.frost.springular.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.frost.springular.object.exception.DuplicatedEmailException;
import com.frost.springular.object.model.UserModel;
import com.frost.springular.object.repository.UserRepository;
import com.frost.springular.object.request.LoginRequest;
import com.frost.springular.object.request.SignupRequest;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository, PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public UserModel register(SignupRequest signupDTO)
            throws DuplicatedEmailException {
        if (userRepository.findByEmail(signupDTO.getEmail()).isPresent()) {
            throw new DuplicatedEmailException();
        }

        return userRepository.save(
                UserModel.builder()
                        .firstName(signupDTO.getFirstName())
                        .lastName(signupDTO.getLastName())
                        .dateOfBirth(signupDTO.getDateOfBirth())
                        .email(signupDTO.getEmail())
                        .password(passwordEncoder.encode(signupDTO.getPassword()))
                        .build());
    }

    public UserModel authenticate(LoginRequest loginDto) {
        // TODO: implement business logic for dto objects
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(), loginDto.getPassword()));
        return userRepository.findByEmail(loginDto.getEmail()).orElseThrow();
    }
}
