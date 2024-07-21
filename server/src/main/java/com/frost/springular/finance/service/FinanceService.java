package com.frost.springular.finance.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

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
      default -> filterTransactionsByInterval("month", startOfInterval);
    };
  }

  public TransactionModel create(TransactionRequest transactionRequest) {
    createRootGroup();

    TransactionGroupModel group = transactionGroupRepository
        .findByUserAndPath(userService.getCurrentUser(), transactionRequest.getGroupPath())
        .orElseThrow(() -> new FinanceException("Group not found"));

    TransactionModel transaction = transactionRepository.save(conversionService.convert(
        Triplet.of(transactionRequest, group, userService.getCurrentUser()),
        TransactionModel.class));

    transactionGroupRepository.updateRevenuesAndExpensesById(
        group.getRevenues() + transaction.getRevenues(),
        group.getExpenses() + transaction.getExpenses(),
        group.getId());

    return transaction;
  }

  public TransactionGroupModel create(TransactionGroupRequest groupRequest) {
    TransactionGroupModel groupModel = conversionService.convert(
        Pair.of(groupRequest, userService.getCurrentUser()),
        TransactionGroupModel.class);

    if (groupModel.getLevel() < 1) {
      throw new FinanceException("Invalid group path");
    }

    return transactionGroupRepository.save(groupModel);
  }

  public TransactionGroupModel update(String id, TransactionGroupRequest groupRequest) {
    createRootGroup();

    TransactionGroupModel groupModel = findGroupById(id)
        .orElseThrow(() -> new FinanceException(
            "Could not find transaction group"));

    if (groupModel.getLevel() == 0) {
      throw new FinanceException("Invalid group path");
    }

    String oldPath = groupModel.getPath();

    groupModel.setPath(groupRequest.getPath());
    if (groupModel.getLevel() == 0) {
      throw new FinanceException("Invalid group path");
    }

    groupModel.setDescription(groupRequest.getDescription());
    groupModel = transactionGroupRepository.save(groupModel);

    String newPath = groupModel.getPath();

    if (!oldPath.equals(groupModel.getPath())) {
      transactionGroupRepository.findByUserAndPathStartingWith(
          groupModel.getUser(), oldPath).forEach(childGroupModel -> {
            childGroupModel.setPath(
                childGroupModel.getPath().replaceFirst(oldPath, newPath));
          });
    }

    return groupModel;
  }

  public TransactionModel update(String id, TransactionRequest transactionRequest) {
    createRootGroup();

    TransactionGroupModel groupModel = transactionGroupRepository
        .findByUserAndPath(userService.getCurrentUser(), transactionRequest.getGroupPath())
        .orElseThrow(() -> new FinanceException("Group not found"));

    TransactionModel transactionModel = transactionRepository.findById(id)
        .orElseThrow(() -> new FinanceException("Transaction not found"));

    boolean groupChanged = !transactionRequest.getGroupPath().equals(
        transactionModel.getGroup().getPath());
    String oldGroupId = transactionModel.getGroup().getId();
    double oldRevenues = transactionModel.getRevenues();
    double oldExpenses = transactionModel.getExpenses();

    transactionModel.setGroup(groupModel);
    transactionModel.setNote(transactionRequest.getNote());
    transactionModel.setRevenues(transactionRequest.getRevenues());
    transactionModel.setExpenses(transactionRequest.getExpenses());
    transactionModel = transactionRepository.save(transactionModel);

    groupModel.setRevenues(groupModel.getRevenues() + transactionModel.getRevenues());
    groupModel.setExpenses(groupModel.getExpenses() + transactionModel.getExpenses());
    transactionGroupRepository.save(groupModel);

    if (groupChanged) {
      transactionGroupRepository.updateRevenuesAndExpensesById(
          -oldRevenues, -oldExpenses, oldGroupId);
    }

    return transactionModel;
  }

  public void delete(String id, Class<?> clazz) {
    if (clazz == TransactionModel.class) {
      transactionRepository.findById(id).ifPresent((transactionModel) -> {
        String groupId = transactionModel.getGroup().getId();
        double oldRevenues = transactionModel.getRevenues();
        double oldExpenses = transactionModel.getExpenses();

        transactionRepository.deleteById(id);
        transactionGroupRepository.updateRevenuesAndExpensesById(
            -oldRevenues, -oldExpenses, groupId);
      });

    } else if (clazz == TransactionGroupModel.class) {
      findGroupById(id).ifPresent((groupModel) -> {
        if (groupModel.getLevel() == 0) {
          transactionRepository.deleteAllByUser(userService.getCurrentUser());
          transactionGroupRepository.deleteAllByUser(
              userService.getCurrentUser());
        } else {
          transactionRepository.deleteAllByUserAndGroupPathStartingWith(
              userService.getCurrentUser(), groupModel.getPath());
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
