package com.frost.springular.finance.data.model;

import org.springframework.stereotype.Repository;

import com.frost.springular.user.data.model.UserModel;

import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TransactionRepository
    extends CrudRepository<TransactionModel, String> {
  List<TransactionModel> findByGroup(TransactionGroupModel group);

  List<TransactionModel> findByUser(UserModel user);

  List<TransactionModel> findByUserAndTimeGreaterThanEqualAndTimeLessThan(
      UserModel user, Instant start, Instant end);

  void deleteAllByUser(UserModel user);

  void deleteAllByUserAndGroupPathStartingWith(UserModel user, String path);
}
