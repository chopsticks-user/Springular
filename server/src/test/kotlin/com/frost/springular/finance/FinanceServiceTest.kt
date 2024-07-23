package com.frost.springular.finance

import com.frost.springular.finance.data.model.TransactionGroupModel
import com.frost.springular.finance.data.model.TransactionGroupRepository
import com.frost.springular.finance.data.model.TransactionRepository
import com.frost.springular.finance.data.request.TransactionGroupRequest
import com.frost.springular.finance.exception.FinanceException
import com.frost.springular.finance.service.FinanceService
import com.frost.springular.shared.util.tuple.Pair
import com.frost.springular.user.data.model.UserModel
import com.frost.springular.user.service.UserService
import io.jsonwebtoken.lang.Collections
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import org.springframework.core.convert.ConversionService
import java.util.*

@ExtendWith(MockitoExtension::class)
class FinanceServiceTest() {
    @Mock
    private lateinit var transactionRepository: TransactionRepository

    @Mock
    private lateinit var transactionGroupRepository: TransactionGroupRepository

    @Mock
    private lateinit var userService: UserService

    @Mock
    private lateinit var conversionService: ConversionService

    @InjectMocks
    private lateinit var financeService: FinanceService

    @Test
    fun when_trying_to_create_root_group() {
        val request = mock<TransactionGroupRequest>()
        val group = mock<TransactionGroupModel>()
        val user = mock<UserModel>()

        whenever(group.level).thenReturn(0)
        whenever(userService.currentUser).thenReturn(user)
        whenever(
            conversionService.convert(
                Pair.of(request, user),
                TransactionGroupModel::class.java
            )
        ).thenReturn(group)

        assertThrows<FinanceException> { financeService.create(request) }
    }

    @Test
    fun when_new_group_does_not_have_a_parent() {
        val newGroupPath = "/group/new"
        val parentGroupPath = "/group"

        val request = mock<TransactionGroupRequest>()
        val group = mock<TransactionGroupModel>()
        val user = mock<UserModel>()

        whenever(group.level).thenReturn(2)
        whenever(group.path).thenReturn(newGroupPath)
        whenever(userService.currentUser).thenReturn(user)
        whenever(
            transactionGroupRepository.findByUserAndPath(
                user, parentGroupPath
            )
        ).thenReturn(Optional.empty())
        Mockito.lenient().`when`(
            conversionService.convert(
                Pair.of(request, user),
                TransactionGroupModel::class.java
            )
        )
            .thenReturn(group)

        assertThrows<FinanceException> { financeService.create(request) }
    }

    @Test
    fun when_transaction_does_not_belong_to_an_existed_group() {
    }

    @Test
    fun ensures_transaction_is_created_as_expected() {
    }

    @Test
    fun ensures_root_level_transaction_is_created_as_expected() {
    }

    @Test
    fun when_group_with_id_not_found() {
        val groupId = ArgumentMatchers.anyString()

        whenever(transactionGroupRepository.findById(groupId))
            .thenReturn(Optional.empty())

        Assertions.assertThrows(
            FinanceException::class.java
        ) { financeService.findGroupByIdThrowIfNot(groupId) }
    }

    @Test
    fun ensures_all_groups_are_returned_in_correct_order() {
        val allGroups = mock<List<TransactionGroupModel>>()
        val user = mock<UserModel>()

        whenever(userService.currentUser).thenReturn(user)
        whenever(
            transactionGroupRepository
                .findByUserOrderByLevel(userService.currentUser)
        )
            .thenReturn(allGroups)

        Assertions.assertEquals(allGroups, financeService.allGroups)
    }

    @Test
    fun when_group_has_no_children() {
        val children = Collections.emptyList<TransactionGroupModel>()
        val user = mock<UserModel>()
        val group = mock<TransactionGroupModel>()

        whenever(group.revenues).thenReturn(100.0)
        whenever(group.expenses).thenReturn(200.0)
        whenever(group.user).thenReturn(user)
        whenever(group.path).thenReturn("/some-group")
        whenever(
            transactionGroupRepository.findByUserAndPathStartingWith(
                group.user, group.path
            )
        ).thenReturn(children)

        Assertions.assertEquals(
            Pair.of(group.revenues, group.expenses),
            financeService.getActualRevenuesAndExpenses(group)
        )
    }

    @Test
    fun when_group_has_some_children() {
        val childCount = 5
        val eachChildRevenues = 120.0
        val eachChildExpenses = 90.0

        val children: MutableList<TransactionGroupModel> = ArrayList()

        for (i in 0 until childCount) {
            val child = mock<TransactionGroupModel>()
            whenever(child.revenues).thenReturn(eachChildRevenues)
            whenever(child.expenses).thenReturn(eachChildExpenses)
            children.add(child)
        }

        val user = mock<UserModel>()
        val group = mock<TransactionGroupModel>()

        whenever(group.revenues).thenReturn(500.0)
        whenever(group.expenses).thenReturn(200.0)
        whenever(group.user).thenReturn(user)
        whenever(group.path).thenReturn("/some-group")
        whenever(
            transactionGroupRepository.findByUserAndPathStartingWith(
                group.user, group.path
            )
        ).thenReturn(children)

        Assertions.assertEquals(
            Pair.of(
                group.revenues + eachChildRevenues * childCount,
                group.expenses + eachChildExpenses * childCount
            ),
            financeService.getActualRevenuesAndExpenses(group)
        )
    }
}