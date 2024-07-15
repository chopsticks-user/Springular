package com.frost.springular.object.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.frost.springular.object.model.RefreshTokenModel;
import com.frost.springular.object.model.UserModel;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface RefreshTokenRepository
        extends CrudRepository<RefreshTokenModel, String> {
    Optional<RefreshTokenModel> findByToken(String token);

    Optional<RefreshTokenModel> findByUserEntity(UserModel userEntity);
}
