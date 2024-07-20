package com.frost.springular.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.frost.springular.exception.FinanceException;
import com.frost.springular.model.TransactionGroupModel;
import com.frost.springular.repository.TransactionGroupRepository;
import com.frost.springular.model.TransactionModel;
import com.frost.springular.repository.TransactionRepository;
import com.frost.springular.model.UserModel;

@Service
public class FinanceService {
  private final TransactionRepository transactionRepository;
  private final TransactionGroupRepository transactionGroupRepository;

  public FinanceService(TransactionRepository transactionRepository,
      TransactionGroupRepository transactionGroupRepository) {
    this.transactionRepository = transactionRepository;
    this.transactionGroupRepository = transactionGroupRepository;
  }

  public Optional<TransactionModel> findTransactionById(String id) {
    return transactionRepository.findById(id);
  }

  public Optional<TransactionGroupModel> findTransactionGroupById(String id) {
    return transactionGroupRepository.findById(id);
  }

  public List<TransactionGroupModel> getAllTransactionGroups(
      UserModel userModel) {
    return transactionGroupRepository.findByUser(userModel);
  }

  public List<TransactionModel> filterTransactionsByInterval(
      String interval, Instant startOfInterval, UserModel userModel) {
    return switch (interval) {
      case "month" -> transactionRepository.filterBetween(
          userModel, startOfInterval,
          startOfInterval.plus(1, ChronoUnit.MONTHS));
      case "year" -> transactionRepository.filterBetween(
          userModel, startOfInterval,
          startOfInterval.plus(1, ChronoUnit.YEARS));
      default -> transactionRepository.filterBetween(
          userModel, startOfInterval,
          startOfInterval.plus(1, ChronoUnit.MONTHS));
    };
  }

  public TransactionModel save(
      TransactionModel transactionModel, UserModel userModel) {
    return transactionRepository.save(transactionModel);
  }

  public TransactionGroupModel save(
      TransactionGroupModel transactionGroupModel, UserModel userModel) {
    transactionGroupModel.setUser(userModel);
    return save(transactionGroupModel);
  }

  public TransactionGroupModel save(TransactionGroupModel transactionGroupModel) {
    setAndValidateGroupPath(transactionGroupModel);
    return transactionGroupRepository.save(transactionGroupModel);
  }

  private void setAndValidateGroupPath(
      TransactionGroupModel transactionGroupModel) {
    if (transactionGroupModel.getParentId() == null) {
      transactionGroupModel.setPath("/");
    } else {
      TransactionGroupModel parentModel = transactionGroupRepository
          .findById(transactionGroupModel.getParentId())
          .orElseThrow(() -> new FinanceException("Parent group not found"));

      String parentPath = parentModel.getPath();
      if (parentPath.endsWith("/")) {
        transactionGroupModel.setPath(
            parentPath + transactionGroupModel.getName());
      } else {
        transactionGroupModel.setPath(
            parentPath + "/" + transactionGroupModel.getName());
      }
    }

    transactionGroupRepository
        .findByPath(transactionGroupModel.getPath())
        .ifPresent((model) -> {
          throw new FinanceException("Duplicated group path");
        });
  }
}
