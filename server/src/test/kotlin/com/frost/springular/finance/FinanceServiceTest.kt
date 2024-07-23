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
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import org.springframework.core.convert.ConversionService

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
}