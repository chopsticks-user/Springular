package com.frost.springular.object.repository;

import org.springframework.stereotype.Repository;

import com.frost.springular.object.model.TransactionGroupModel;
import com.frost.springular.object.model.TransactionModel;
import com.frost.springular.object.model.UserModel;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

@Repository
public interface TransactionRepository
    extends CrudRepository<TransactionModel, String> {
  List<TransactionModel> findByUserModel(UserModel userModel);

  List<TransactionModel> findByParentId(String parentId);

  @Query(value = """
      SELECT t FROM TransactionModel t
        WHERE t.userModel = :user
        AND t.time >= :start
        AND t.time < :end
      """)
  List<TransactionModel> filterBetween(UserModel user, Instant start, Instant end);

}
