package com.frost.springular.repository;

import org.springframework.stereotype.Repository;

import com.frost.springular.model.TransactionGroupModel;
import com.frost.springular.model.TransactionModel;
import com.frost.springular.model.UserModel;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

@Repository
public interface TransactionRepository
    extends CrudRepository<TransactionModel, String> {
  List<TransactionModel> findByGroup(TransactionGroupModel group);

  List<TransactionModel> findByUser(UserModel user);

  @Query(value = """
      SELECT t FROM TransactionModel t
        WHERE t.user = :user
        AND t.time >= :start
        AND t.time < :end
      """)
  List<TransactionModel> filterBetween(
      UserModel user, Instant start, Instant end);

}
