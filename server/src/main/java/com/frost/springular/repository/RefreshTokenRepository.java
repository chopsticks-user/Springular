package com.frost.springular.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.frost.springular.model.RefreshTokenModel;
import com.frost.springular.model.UserModel;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface RefreshTokenRepository
    extends CrudRepository<RefreshTokenModel, String> {
  Optional<RefreshTokenModel> findByToken(String token);

  Optional<RefreshTokenModel> findByUserEntity(UserModel userEntity);
}
