package com.frost.springular.user.data.model;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserModel, String> {
  Optional<UserModel> findByEmail(String email);
}
