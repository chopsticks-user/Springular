package com.frost.springular.object.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.frost.springular.object.model.TransactionGroupModel;
import com.frost.springular.object.model.UserModel;

@Repository
public interface TransactionGroupRepository
    extends CrudRepository<TransactionGroupModel, String> {
  List<TransactionGroupModel> findByUser(UserModel userModel);

  List<TransactionGroupModel> findByParentId(String parentId);
}
