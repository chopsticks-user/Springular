package com.frost.springular.finance.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.frost.springular.finance.data.model.TransactionGroupModel;
import com.frost.springular.finance.data.model.TransactionGroupRepository;
import com.frost.springular.finance.data.model.TransactionModel;
import com.frost.springular.finance.data.model.TransactionRepository;
import com.frost.springular.finance.data.request.TransactionGroupRequest;
import com.frost.springular.finance.data.request.TransactionRequest;
import com.frost.springular.finance.exception.FinanceException;
import com.frost.springular.shared.util.tuple.Pair;
import com.frost.springular.shared.util.tuple.Triplet;
import com.frost.springular.user.data.model.UserModel;
import com.frost.springular.user.service.UserService;

@Service
public class FinanceService {
  // private static final Comparator<TransactionGroupModel> groupComparator = //
  // new Comparator<TransactionGroupModel>() {
  // @Override
  // public int compare(TransactionGroupModel a, TransactionGroupModel b) {
  // return a.getLevel() - b.getLevel();
  // }
  // };

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

  public TransactionGroupModel findGroupByIdThrowIfNot(String id) {
    return transactionGroupRepository.findById(id).orElseThrow(
        () -> new FinanceException("Group not found"));
  }

  public Optional<TransactionGroupModel> findGroupByPath(String path) {
    return transactionGroupRepository
        .findByUserAndPath(userService.getCurrentUser(), path);
  }

  public List<TransactionGroupModel> getAllGroups() {
    createRootGroup();
    return transactionGroupRepository.findByUserOrderByLevel(
        userService.getCurrentUser());
  }

  public Pair<Double, Double> getActualRevenuesAndExpenses(
      TransactionGroupModel groupModel) {
    Pair<Double, Double> pair = Pair.of(0.0, 0.0);

    transactionGroupRepository.findByUserAndPathStartingWith(
        groupModel.getUser(), groupModel.getPath()).forEach(childGroupModel -> {
          pair.setFirst(pair.getFirst() + childGroupModel.getRevenues());
          pair.setSecond(pair.getFirst() + childGroupModel.getExpenses());
        });

    return pair;
  }

  public Pair<Double, Double> getActualRevenuesAndExpenses(String id) {
    return getActualRevenuesAndExpenses(findGroupByIdThrowIfNot(id));
  }

  public List<Pair<Double, Double>> getActualRevenuesAndExpenses(
      List<TransactionGroupModel> ascSortedGroupModels) {
    if (ascSortedGroupModels.size() == 0) {
      return List.of();
    }

    // for (int i = 1; i < ascSortedGroupModels.size(); ++i) {
    // if (ascSortedGroupModels
    // .get(i - 1).getLevel() > ascSortedGroupModels
    // .get(i).getLevel()) {
    // Collections.sort(ascSortedGroupModels, groupComparator);
    // break;
    // }
    // }

    // for (int i = ascSortedGroupModels.size() - 1; i > -1; --i) {
    // }

    var groupList = ascSortedGroupModels;
    if (!IntStream.range(0, ascSortedGroupModels.size() - 1)
        .allMatch(i -> groupList
            .get(i).getLevel() <= groupList
                .get(i + 1).getLevel())) {
      throw new RuntimeException("");
    }

    var valueMap = new HashMap<String, Pair<Double, Double>>();
    int highestLevel = ascSortedGroupModels.getLast().getLevel();
    return ascSortedGroupModels.reversed().stream().sequential()
        .map(groupModel -> {
          String path = groupModel.getPath();
          String parentPath = groupModel.getPath().substring(
              0,
              groupModel.getPath().lastIndexOf('/', path.length() - 1));

          var parentEntry = valueMap.get(parentPath);
          if (parentEntry == null) {
            parentEntry = valueMap.put(
                parentPath, Pair.of(0.0, 0.0));
          }

          parentEntry.setFirst(
              parentEntry.getFirst() + groupModel.getRevenues());
          parentEntry.setSecond(
              parentEntry.getSecond() + groupModel.getExpenses());

          if (groupModel.getLevel() == highestLevel) {
            return Pair.of(groupModel.getRevenues(), groupModel.getExpenses());
          }
          return valueMap.get(path);
        }).toList().reversed();
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
