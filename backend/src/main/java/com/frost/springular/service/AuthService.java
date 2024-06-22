package com.frost.springular.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.frost.springular.dto.LoginRequestDTO;
import com.frost.springular.dto.SignupRequestDTO;
import com.frost.springular.entity.UserEntity;
import com.frost.springular.exception.DuplicatedEmailException;
import com.frost.springular.repository.UserRepository;

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

    public UserEntity register(SignupRequestDTO signupDTO)
            throws DuplicatedEmailException {
        if (userRepository.findByEmail(signupDTO.email()).isPresent()) {
            throw new DuplicatedEmailException();
        }

        return userRepository.save(new UserEntity()
                .setFirstname(signupDTO.firstName())
                .setLastname(signupDTO.lastName())
                .setDateOfBirth(signupDTO.dateOfBirth())
                .setEmail(signupDTO.email())
                .setPassword(passwordEncoder.encode(signupDTO.password())));
    }

    public UserEntity authenticate(LoginRequestDTO loginDTO) {
        // TODO: implement business logic for dto objects
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
        return userRepository.findByEmail(loginDTO.email()).orElseThrow();
    }
}
