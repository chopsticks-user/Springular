package com.frost.springular.finance.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.frost.springular.finance.data.model.TransactionGroupModel;
import com.frost.springular.finance.data.model.TransactionGroupRepository;
import com.frost.springular.finance.data.model.TransactionModel;
import com.frost.springular.finance.data.model.TransactionRepository;
import com.frost.springular.finance.data.request.TransactionGroupRequest;
import com.frost.springular.finance.data.request.TransactionRequest;
import com.frost.springular.finance.exception.FinanceException;
import com.frost.springular.shared.tuple.Pair;
import com.frost.springular.shared.tuple.Triplet;
import com.frost.springular.user.data.model.UserModel;
import com.frost.springular.user.service.UserService;

@Service
public class FinanceService {
  private final TransactionRepository transactionRepository;
  private final TransactionGroupRepository transactionGroupRepository;
  private final UserService userService;
  private final ConversionService conversionService;

  public FinanceService(
      TransactionRepository transactionRepository,
      TransactionGroupRepository transactionGroupRepository,
      UserService userService,
      ConversionService conversionService) {
    this.transactionRepository = transactionRepository;
    this.transactionGroupRepository = transactionGroupRepository;
    this.userService = userService;
    this.conversionService = conversionService;
  }

  public Optional<TransactionModel> findTransactionById(String id) {
    return transactionRepository.findById(id);
  }

  public Optional<TransactionGroupModel> findGroupById(String id) {
    return transactionGroupRepository.findById(id);
  }

  public Optional<TransactionGroupModel> findGroupByPath(String path) {
    return transactionGroupRepository
        .findByUserAndPath(userService.getCurrentUser(), path);
  }

  public List<TransactionGroupModel> getAllGroups() {
    createRootGroup();
    return transactionGroupRepository.findByUser(userService.getCurrentUser());
  }

  public List<TransactionModel> filterTransactionsByInterval(
      String interval, Instant startOfInterval) {
    final UserModel userModel = userService.getCurrentUser();
    return switch (interval) {
      case "month" -> transactionRepository.findByUserAndTimeGreaterThanEqualAndTimeLessThan(
          userModel, startOfInterval,
          startOfInterval.plus(1, ChronoUnit.MONTHS));
      case "year" -> transactionRepository.findByUserAndTimeGreaterThanEqualAndTimeLessThan(
          userModel, startOfInterval,
          startOfInterval.plus(1, ChronoUnit.YEARS));
      default -> transactionRepository.findByUserAndTimeGreaterThanEqualAndTimeLessThan(
          userModel, startOfInterval,
          startOfInterval.plus(1, ChronoUnit.MONTHS));
    };
  }

  public TransactionModel create(TransactionRequest transactionRequest) {
    createRootGroup();

    TransactionModel transaction = transactionRepository.save(conversionService.convert(
        Triplet.of(transactionRequest, null, userService.getCurrentUser()),
        TransactionModel.class));

    return transaction;
  }

  public TransactionGroupModel create(TransactionGroupRequest groupRequest) {
    return transactionGroupRepository.save(conversionService.convert(
        Pair.of(groupRequest, userService.getCurrentUser()),
        TransactionGroupModel.class));
  }

  public TransactionGroupModel update(
      String id, TransactionGroupRequest groupRequest) {
    createRootGroup();

    TransactionGroupModel groupModel = findGroupById(id)
        .orElseThrow(() -> new FinanceException(
            "Could not find transaction group"));

    groupModel.setPath(groupRequest.getPath());
    groupModel.setDescription(groupRequest.getDescription());

    return transactionGroupRepository.save(groupModel);
  }

  public void delete(String id, Class<?> clazz) {
    if (clazz == TransactionModel.class) {
      transactionRepository.deleteById(id);
    } else if (clazz == TransactionGroupModel.class) {
      findGroupById(id).ifPresent((groupModel) -> {
        if (groupModel.getLevel() == 0) {
          transactionGroupRepository.deleteAllByUser(
              userService.getCurrentUser());
        } else {
          transactionGroupRepository.deleteAllByPathStartingWith(
              groupModel.getPath());
        }
      });
    }
  }

  private void createRootGroup() {
    if (transactionGroupRepository
        .findByUserAndLevel(userService.getCurrentUser(), 0).size() != 0) {
      return;
    }

    transactionGroupRepository.save(
        TransactionGroupModel.builder()
            .path("/")
            .revenues(0)
            .expenses(0)
            .level(0)
            .user(userService.getCurrentUser())
            .build());
  }
}
