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

  List<TransactionGroupModel> findByParentId(String parentId);

  Optional<TransactionGroupModel> findByParentIdAndName(
      String parentId, String name);

  void deleteAllByParentId(String parentId);
}
