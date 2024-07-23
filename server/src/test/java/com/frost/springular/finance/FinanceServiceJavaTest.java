package com.frost.springular.finance;

import com.frost.springular.finance.data.model.TransactionGroupModel;
import com.frost.springular.finance.data.model.TransactionGroupRepository;
import com.frost.springular.finance.data.model.TransactionRepository;
import com.frost.springular.finance.data.request.TransactionGroupRequest;
import com.frost.springular.finance.exception.FinanceException;
import com.frost.springular.finance.service.FinanceService;
import com.frost.springular.shared.util.tuple.Pair;
import com.frost.springular.user.data.model.UserModel;
import com.frost.springular.user.service.UserService;
import io.jsonwebtoken.lang.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FinanceServiceJavaTest {
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
    void when_trying_to_create_root_group() {
        TransactionGroupRequest request = mock();
        TransactionGroupModel group = mock();
        UserModel user = mock();

        when(group.getLevel()).thenReturn(0);
        when(userService.getCurrentUser()).thenReturn(user);
        when(conversionService.convert(
                Pair.of(request, user), TransactionGroupModel.class))
                .thenReturn(group);

        assertThrows(FinanceException.class,
                () -> financeService.create(request));
    }

    @Test
    void when_new_group_does_not_have_a_parent() {
        String newGroupPath = "/group/new";
        String parentGroupPath = "/group";

        TransactionGroupRequest request = mock();
        TransactionGroupModel group = mock();
        UserModel user = mock();

        lenient().when(group.getLevel()).thenReturn(2);
        lenient().when(group.getPath()).thenReturn(newGroupPath);
        lenient().when(request.getPath()).thenReturn(newGroupPath);
        lenient().when(userService.getCurrentUser()).thenReturn(user);
        lenient().when(transactionGroupRepository.findByUserAndPath(
                user, parentGroupPath)).thenReturn(Optional.empty());
        lenient().when(conversionService.convert(
                        Pair.of(request, user), TransactionGroupModel.class))
                .thenReturn(group);

        assertThrows(FinanceException.class,
                () -> financeService.create(request));
    }

    @Test
    void ensures_group_is_created_as_expected() {
        String newGroupPath = "/group/new";
        String parentGroupPath = "/group";

        TransactionGroupRequest request = mock();
        TransactionGroupModel group = mock();
        TransactionGroupModel parentGroup = mock();
        UserModel user = mock();

        lenient().when(group.getLevel()).thenReturn(2);
        lenient().when(group.getPath()).thenReturn(newGroupPath);
        lenient().when(request.getPath()).thenReturn(newGroupPath);
        lenient().when(userService.getCurrentUser()).thenReturn(user);
        lenient().when(transactionGroupRepository.findByUserAndPath(
                user, parentGroupPath)).thenReturn(Optional.of(parentGroup));
        lenient().when(conversionService.convert(
                        Pair.of(request, user), TransactionGroupModel.class))
                .thenReturn(group);

        assertDoesNotThrow(() -> financeService.create(request));
    }

    @Test
    void when_transaction_does_not_belong_to_an_existed_group() {
    }

    @Test
    void ensures_transaction_is_created_as_expected() {
    }

    @Test
    void ensures_root_level_transaction_is_created_as_expected() {
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

    @Test
    void when_group_has_some_children() {
        final int childCount = 5;
        final double eachChildRevenues = 120.0;
        final double eachChildExpenses = 90.0;

        List<TransactionGroupModel> children = new ArrayList<>();

        for (int i = 0; i < childCount; ++i) {
            TransactionGroupModel child = mock();
            when(child.getRevenues()).thenReturn(eachChildRevenues);
            when(child.getExpenses()).thenReturn(eachChildExpenses);
            children.add(child);
        }

        UserModel user = mock();
        TransactionGroupModel group = mock();

        when(group.getRevenues()).thenReturn(500.0);
        when(group.getExpenses()).thenReturn(200.0);
        when(group.getUser()).thenReturn(user);
        when(group.getPath()).thenReturn("/some-group");
        when(transactionGroupRepository.findByUserAndPathStartingWith(
                group.getUser(), group.getPath())).thenReturn(children);

        assertEquals(Pair.of(
                        group.getRevenues() + eachChildRevenues * childCount,
                        group.getExpenses() + eachChildExpenses * childCount),
                financeService.getActualRevenuesAndExpenses(group));
    }
}
