package com.frost.springular.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        Instant expiresAt = Instant.now().plusMillis(expirationTime);

        JwtRefreshTokenEntity existingToken = findByUserEmail(userEmail)
                .orElse(null);

        if (isTokenValid(existingToken)) {
            return existingToken;
        }

        return jwtRefreshTokenRepository.save(
                JwtRefreshTokenEntity.builder()
                        .userEntity(userRepository
                                .findByEmail(userEmail)
                                .orElseThrow())
                        .token(UUID.randomUUID().toString())
                        .expirationDate(expiresAt)
                        .build());
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public Optional<JwtRefreshTokenEntity> findByToken(String token) {
        return jwtRefreshTokenRepository.findByToken(token);
    }

    public Optional<JwtRefreshTokenEntity> findByUserEmail(String userEmail) {
        UserEntity userEntity = userRepository
                .findByEmail(userEmail)
                .orElse(null);

        if (userEntity == null) {
            return Optional.empty();
        }

        return jwtRefreshTokenRepository.findByUserEntity(userEntity);
    }

    public Optional<JwtRefreshTokenEntity> findByUserId(String userId) {
        UserEntity userEntity = userRepository
                .findById(userId)
                .orElse(null);

        if (userEntity == null) {
            return Optional.empty();
        }

        return jwtRefreshTokenRepository.findByUserEntity(userEntity);
    }

    public void verifyExpiration(
            JwtRefreshTokenEntity tokenEntity)
            throws JwtRefreshTokenExpiredException {
        if (!isTokenValid(tokenEntity)) {
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

    private boolean isTokenValid(JwtRefreshTokenEntity tokenEntity) {
        return tokenEntity != null
                && tokenEntity.getExpirationDate().compareTo(Instant.now()) > 0;
    }
}
