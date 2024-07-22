package com.frost.springular.user.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.frost.springular.user.data.model.RefreshTokenModel;
import com.frost.springular.user.data.model.RefreshTokenRepository;
import com.frost.springular.user.data.model.UserModel;
import com.frost.springular.user.data.model.UserRepository;
import com.frost.springular.user.exception.RefreshTokenExpiredException;

@Service
public class RefreshTokenService {

  @Autowired
  private RefreshTokenRepository jwtRefreshTokenRepository;

  @Autowired
  private UserRepository userRepository;

  @Value("${spring.security.jwt.expiration.refresh}")
  private long expirationTime;

  public RefreshTokenModel generateToken(String userEmail) {
    Instant expiresAt = Instant.now().plusMillis(expirationTime);

    RefreshTokenModel existingToken = findByUserEmail(userEmail).orElse(null);

    if (isTokenValid(existingToken)) {
      return existingToken;
    }

    return jwtRefreshTokenRepository.save(
        RefreshTokenModel.builder()
            .userEntity(userRepository.findByEmail(userEmail).orElseThrow())
            .token(UUID.randomUUID().toString())
            .expirationDate(expiresAt)
            .build());
  }

  public long getExpirationTime() {
    return expirationTime;
  }

  public Optional<RefreshTokenModel> findByToken(String token) {
    return jwtRefreshTokenRepository.findByToken(token);
  }

  public Optional<RefreshTokenModel> findByUserEmail(String userEmail) {
    UserModel userEntity = userRepository.findByEmail(userEmail).orElse(null);

    if (userEntity == null) {
      return Optional.empty();
    }

    return jwtRefreshTokenRepository.findByUserEntity(userEntity);
  }

  public Optional<RefreshTokenModel> findByUserId(String userId) {
    UserModel userEntity = userRepository.findById(userId).orElse(null);

    if (userEntity == null) {
      return Optional.empty();
    }

    return jwtRefreshTokenRepository.findByUserEntity(userEntity);
  }

  public void verifyExpiration(RefreshTokenModel tokenEntity)
      throws RefreshTokenExpiredException {
    if (!isTokenValid(tokenEntity)) {
      jwtRefreshTokenRepository.delete(tokenEntity);
      throw new RefreshTokenExpiredException();
    }
  }

  public void revokeToken(UserModel userEntity) {
    RefreshTokenModel tokenEntity = jwtRefreshTokenRepository.findByUserEntity(userEntity).orElse(null);
    if (tokenEntity == null) {
      return;
    }

    jwtRefreshTokenRepository.delete(tokenEntity);
  }

  private boolean isTokenValid(RefreshTokenModel tokenEntity) {
    return tokenEntity != null &&
        tokenEntity.getExpirationDate().compareTo(Instant.now()) > 0;
  }
}
