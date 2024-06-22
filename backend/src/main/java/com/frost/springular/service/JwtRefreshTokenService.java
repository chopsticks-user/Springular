package com.frost.springular.service;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.frost.springular.entity.JwtRefreshTokenEntity;
import com.frost.springular.entity.UserEntity;
import com.frost.springular.exception.JwtRefreshTokenExpiredException;
import com.frost.springular.repository.JwtRefreshTokenRepository;
import com.frost.springular.repository.UserRepository;

@Service
public class JwtRefreshTokenService {

    @Autowired
    private JwtRefreshTokenRepository jwtRefreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${security.jwt.refresh.expiration-time}")
    private long expirationTime;

    public JwtRefreshTokenEntity generateToken(String userEmail) {
        // Instant expiresAt = Instant.now().plusMillis(expirationTime);

        // JwtRefreshTokenEntity tokenEntity = JwtRefreshTokenEntity.builder()
        // .userEntity(userRepository
        // .findByEmail(userEmail)
        // .orElseThrow())
        // .token(UUID.randomUUID().toString())
        // .expirationDate(expiresAt)
        // .build();
        // jwtRefreshTokenRepository.save(tokenEntity);

        // return new JwtRefreshTokenDTO(tokenEntity.getToken(),
        // tokenEntity.getExpirationDate());

        Instant expiresAt = Instant.now().plusMillis(expirationTime);

        JwtRefreshTokenEntity tokenEntity = JwtRefreshTokenEntity.builder()
                .userEntity(userRepository
                        .findByEmail(userEmail)
                        .orElseThrow())
                .token(UUID.randomUUID().toString())
                .expirationDate(expiresAt)
                .build();
        return jwtRefreshTokenRepository.save(tokenEntity);
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public Optional<JwtRefreshTokenEntity> findByToken(String token) {
        // JwtRefreshTokenEntity tokenEntity = jwtRefreshTokenRepository
        // .findByToken(token)
        // .orElse(null);
        // return tokenEntity == null
        // ? Optional.empty()
        // : Optional.of(new JwtRefreshTokenDTO(
        // tokenEntity.getToken(), tokenEntity.getExpirationDate()));

        return jwtRefreshTokenRepository.findByToken(token);
    }

    public void verifyExpiration(
            JwtRefreshTokenEntity tokenEntity)
            throws JwtRefreshTokenExpiredException {
        if (tokenEntity.getExpirationDate().compareTo(Instant.now()) < 0) {
            jwtRefreshTokenRepository.delete(tokenEntity);
            throw new JwtRefreshTokenExpiredException();
        }
    }

    public void revokeToken(UserEntity userEntity) {
        JwtRefreshTokenEntity tokenEntity = jwtRefreshTokenRepository
                .findByUserEntity(userEntity)
                .orElse(null);
        if (tokenEntity == null) {
            return;
        }

        jwtRefreshTokenRepository.delete(tokenEntity);
    }
}
