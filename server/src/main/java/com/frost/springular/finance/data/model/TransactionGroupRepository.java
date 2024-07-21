package com.frost.springular.finance.data.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.frost.springular.user.data.model.UserModel;

@Repository
public interface TransactionGroupRepository
    extends CrudRepository<TransactionGroupModel, String> {
  List<TransactionGroupModel> findByUser(UserModel user);

  Optional<TransactionGroupModel> findByUserAndPath(
      UserModel user, String path);

  List<TransactionGroupModel> findByUserAndLevel(
      UserModel user, int level);

  List<TransactionGroupModel> findByUserAndPathStartingWith(
      UserModel user, String path);

  void deleteAllByUser(UserModel user);

  void deleteAllByPathStartingWith(String path);
}
