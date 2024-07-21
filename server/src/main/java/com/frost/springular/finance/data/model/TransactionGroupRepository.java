package com.frost.springular.finance.data.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

  @Modifying
  @Query("""
      UPDATE TransactionGroupModel g
        SET g.revenues = g.revenues + :revenues, g.expenses = g.expenses + :expenses
        WHERE g.id = :id
        """)
  void updateRevenuesAndExpensesById(
      double revenues, double expenses, String id);

  void deleteAllByUser(UserModel user);

  void deleteAllByPathStartingWith(String path);
}
