package com.frost.springular.user.data.model;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface RefreshTokenRepository
    extends CrudRepository<RefreshTokenModel, String> {
  Optional<RefreshTokenModel> findByToken(String token);

  Optional<RefreshTokenModel> findByUserEntity(UserModel userEntity);
}
