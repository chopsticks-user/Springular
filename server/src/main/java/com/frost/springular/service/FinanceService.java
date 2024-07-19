package com.frost.springular.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.frost.springular.object.model.TransactionGroupModel;
import com.frost.springular.object.model.UserModel;
import com.frost.springular.object.repository.TransactionGroupRepository;
import com.frost.springular.object.repository.TransactionRepository;

@Service
public class FinanceService {
  private final TransactionRepository transactionRepository;
  private final TransactionGroupRepository transactionGroupRepository;

  public FinanceService(TransactionRepository transactionRepository,
      TransactionGroupRepository transactionGroupRepository) {
    this.transactionRepository = transactionRepository;
    this.transactionGroupRepository = transactionGroupRepository;
  }

  public List<TransactionGroupModel> getAllTransactionGroups(
      UserModel userModel) {
    return transactionGroupRepository.findByUser(userModel);
  }

  // public List<TransactionGroupModel> saveTransactionGroup() {
  // }
}
