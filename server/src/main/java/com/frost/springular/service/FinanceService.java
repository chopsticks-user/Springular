package com.frost.springular.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.frost.springular.object.model.TransactionGroupModel;
import com.frost.springular.object.model.TransactionModel;
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

  public List<TransactionModel> filterTransactionsByInterval(
      String interval, Instant startOfInterval, UserModel userModel) {
    return switch (interval) {
      case "month" -> this.transactionRepository.filterBetween(
          userModel, startOfInterval,
          startOfInterval.plus(1, ChronoUnit.MONTHS));
      case "year" -> this.transactionRepository.filterBetween(
          userModel, startOfInterval,
          startOfInterval.plus(1, ChronoUnit.YEARS));
      default -> this.transactionRepository.filterBetween(
          userModel, startOfInterval,
          startOfInterval.plus(1, ChronoUnit.MONTHS));
    };
  }

  public TransactionModel save(TransactionModel transactionModel) {
    return this.transactionRepository.save(transactionModel);
  }

  public TransactionGroupModel save(TransactionGroupModel transactionGroupModel) {
    // todo: update path
    return this.transactionGroupRepository.save(transactionGroupModel);
  }
}
