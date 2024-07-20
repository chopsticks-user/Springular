package com.frost.springular.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.frost.springular.exception.FinanceException;
import com.frost.springular.model.TransactionGroupModel;
import com.frost.springular.repository.TransactionGroupRepository;
import com.frost.springular.model.TransactionModel;
import com.frost.springular.repository.TransactionRepository;
import com.frost.springular.request.TransactionGroupRequest;
import com.frost.springular.model.UserModel;

@Service
public class FinanceService {
  private final TransactionRepository transactionRepository;
  private final TransactionGroupRepository transactionGroupRepository;
  private final UserService userService;

  public FinanceService(
      TransactionRepository transactionRepository,
      TransactionGroupRepository transactionGroupRepository,
      UserService userService) {
    this.transactionRepository = transactionRepository;
    this.transactionGroupRepository = transactionGroupRepository;
    this.userService = userService;
  }

  public Optional<TransactionModel> findTransactionById(String id) {
    return transactionRepository.findById(id);
  }

  public Optional<TransactionGroupModel> findGroupById(String id) {
    return transactionGroupRepository.findById(id);
  }

  public List<TransactionGroupModel> findGroupByParentId(String parentId) {
    return transactionGroupRepository.findByParentId(parentId);
  }

  public Optional<TransactionGroupModel> findGroupByPath(
      String parentId, String name) {
    return transactionGroupRepository.findByParentIdAndName(parentId, name);
  }

  public List<TransactionGroupModel> getAllGroups() {
    return transactionGroupRepository.findByUser(userService.getCurrentUser());
  }

  public List<TransactionModel> filterTransactionsByInterval(
      String interval, Instant startOfInterval) {
    final UserModel userModel = userService.getCurrentUser();
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

  public TransactionModel save(TransactionModel transactionModel) {
    return transactionRepository.save(transactionModel);
  }

  public TransactionGroupModel create(TransactionGroupRequest groupRequest) {
    validateGroupPath(groupRequest.getParentId(), groupRequest.getName());

    return transactionGroupRepository.save(
        TransactionGroupModel.builder()
            .name(groupRequest.getName())
            .description(groupRequest.getDescription())
            .revenues(groupRequest.getRevenues())
            .expenses(groupRequest.getExpenses())
            .parentId(groupRequest.getParentId())
            .user(userService.getCurrentUser())
            .build());
  }

  public TransactionGroupModel update(
      String id, TransactionGroupRequest groupRequest) {
    TransactionGroupModel groupModel = findGroupById(id)
        .orElseThrow(() -> new FinanceException(
            "Could not find transaction group"));

    if (!groupModel.getName().equals(groupRequest.getName())
        || !groupModel.getParentId().equals(groupRequest.getParentId())) {
      validateGroupPath(groupRequest.getParentId(), groupRequest.getName());
    }

    groupModel.setName(groupRequest.getName());
    groupModel.setDescription(groupRequest.getDescription());
    groupModel.setRevenues(groupRequest.getRevenues());
    groupModel.setExpenses(groupRequest.getExpenses());
    groupModel.setParentId(groupRequest.getParentId());

    return transactionGroupRepository.save(groupModel);
  }

  public String getGroupPath(TransactionGroupModel groupModel) {
    if (groupModel == null || groupModel.getParentId() == null) {
      return "/";
    }

    var segments = new ArrayList<String>();
    Optional<TransactionGroupModel> currentGroup = Optional.of(groupModel);
    String currentSegment = currentGroup.get().getName();

    while (currentGroup.isPresent()) {
      segments.add(currentGroup.get().getName());
      currentGroup = findGroupById(currentSegment);
    }

    return String.join("/", segments.reversed());
  }

  // todo:
  public Map<String, String> getGroupPaths(List<TransactionGroupModel> groups) {
    Map<String, TransactionGroupModel> groupMap = groups.stream()
        .collect(Collectors.toMap(
            TransactionGroupModel::getId,
            (group) -> group));

    return groups.stream().collect(
        Collectors.toMap(TransactionGroupModel::getId,
            (group) -> {
              if (group.getParentId() == null) {
                return "/";
              }

              var segments = new ArrayList<String>();
              var currentGroup = group;

              while (currentGroup.getParentId() != null) {
                segments.add(group.getName());
                currentGroup = groupMap.get(group.getParentId());
              }

              return String.join("/", segments.reversed());
            }));
  }

  private void validateGroupPath(String parentId, String name) {
    findGroupByPath(parentId, name).ifPresent((group) -> {
      throw new FinanceException("Duplicated paths are not allowed");
    });
  }
}
