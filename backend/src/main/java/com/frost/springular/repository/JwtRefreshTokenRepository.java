package com.frost.springular.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.frost.springular.entity.JwtRefreshTokenEntity;
import com.frost.springular.entity.UserEntity;

@Repository
public interface JwtRefreshTokenRepository
        extends CrudRepository<JwtRefreshTokenEntity, String> {
    Optional<JwtRefreshTokenEntity> findByToken(String token);

    Optional<JwtRefreshTokenEntity> findByUserEntity(UserEntity userEntity);
}
