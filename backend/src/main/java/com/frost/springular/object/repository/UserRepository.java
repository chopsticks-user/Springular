package com.frost.springular.object.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.frost.springular.object.model.UserModel;

@Repository
public interface UserRepository extends CrudRepository<UserModel, String> {
    Optional<UserModel> findByEmail(String email);
}
