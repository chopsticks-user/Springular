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

  public TransactionModel create(TransactionRequest transactionRequest) {
    return transactionRepository.save(conversionService.convert(
        Triplet.of(transactionRequest, null, userService.getCurrentUser()),
        TransactionModel.class));
  }

  public TransactionGroupModel create(TransactionGroupRequest groupRequest) {
    validateGroupPath(groupRequest.getParentId(), groupRequest.getName());

    return transactionGroupRepository.save(conversionService.convert(
        Pair.of(groupRequest, userService.getCurrentUser()),
        TransactionGroupModel.class));
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

  public void deleteTransactionGroup(String id) {
    // todo: children groups
    transactionGroupRepository.deleteById(id);
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
