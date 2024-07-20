package com.frost.springular.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.frost.springular.model.TransactionGroupModel;
import com.frost.springular.model.UserModel;

@Repository
public interface TransactionGroupRepository
    extends CrudRepository<TransactionGroupModel, String> {
  List<TransactionGroupModel> findByUser(UserModel user);

  List<TransactionGroupModel> findByParentId(String parentId);

  Optional<TransactionGroupModel> findByPath(String path);
}
