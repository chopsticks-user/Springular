package com.frost.springular.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import com.frost.springular.finance.data.model.TransactionGroupModel;
import com.frost.springular.finance.data.model.TransactionGroupRepository;
import com.frost.springular.finance.data.model.TransactionRepository;
import com.frost.springular.finance.exception.FinanceException;
import com.frost.springular.finance.service.FinanceService;
import com.frost.springular.shared.util.tuple.Pair;
import com.frost.springular.user.data.model.UserModel;
import com.frost.springular.user.service.UserService;

import io.jsonwebtoken.lang.Collections;

@ExtendWith(MockitoExtension.class)
public class FinanceServiceTest {

  private @Mock TransactionRepository transactionRepository;

  private @Mock TransactionGroupRepository transactionGroupRepository;

  private @Mock UserService userService;

  private @Mock ConversionService conversionService;

  private FinanceService financeService;

  @BeforeEach
  void initialize() {
    financeService = new FinanceService(
        transactionRepository,
        transactionGroupRepository,
        userService,
        conversionService);
  }

  @Test
  void when_group_with_id_not_found() {
    String groupId = anyString();

    when(transactionGroupRepository.findById(groupId))
        .thenReturn(Optional.empty());

    assertThrows(FinanceException.class,
        () -> financeService.findGroupByIdThrowIfNot(groupId));
  }

  @Test
  void ensures_all_groups_are_returned_in_correct_order() {
    List<TransactionGroupModel> allGroups = mock();
    UserModel user = mock();

    when(userService.getCurrentUser()).thenReturn(user);
    when(transactionGroupRepository
        .findByUserOrderByLevel(userService.getCurrentUser()))
        .thenReturn(allGroups);

    assertEquals(allGroups, financeService.getAllGroups());
  }

  @Test
  void when_group_has_no_children() {
    List<TransactionGroupModel> children = Collections.emptyList();
    UserModel user = mock();
    TransactionGroupModel group = mock();

    when(group.getRevenues()).thenReturn(100.0);
    when(group.getExpenses()).thenReturn(200.0);
    when(group.getUser()).thenReturn(user);
    when(group.getPath()).thenReturn("/some-group");
    when(transactionGroupRepository.findByUserAndPathStartingWith(
        group.getUser(), group.getPath())).thenReturn(children);

    assertEquals(Pair.of(group.getRevenues(), group.getExpenses()),
        financeService.getActualRevenuesAndExpenses(group));
  }
}
