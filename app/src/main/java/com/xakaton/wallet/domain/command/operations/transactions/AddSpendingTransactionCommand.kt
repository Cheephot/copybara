package com.xakaton.wallet.domain.command.operations.transactions

import arrow.core.Either
import com.xakaton.wallet.data.remote.dto.AddTransactionRequest
import com.xakaton.wallet.data.remote.services.TransactionService
import com.xakaton.wallet.di.IoDispatcher
import com.xakaton.wallet.domain.command.RemoteCommand
import com.xakaton.wallet.domain.command.RemoteCommandHandler
import com.xakaton.wallet.domain.models.SpendingType
import com.xakaton.wallet.domain.models.Transaction
import com.xakaton.wallet.domain.models.Transaction.Companion.mapToTransaction
import com.xakaton.wallet.domain.utils.rest.RestUtils
import com.xakaton.wallet.domain.utils.rest.TechnicalError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import javax.inject.Inject

data class AddSpendingTransactionCommand(
    val type: SpendingType,
    val amount: BigDecimal,
    val budgetId: String,
) : RemoteCommand<Transaction>

class AddSpendingTransactionCommandHandler @Inject constructor(
    private val transactionService: TransactionService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : RemoteCommandHandler<AddSpendingTransactionCommand, Transaction> {
    override suspend fun handle(command: AddSpendingTransactionCommand): Either<TechnicalError, Transaction> {
        return withContext(dispatcher) {
            RestUtils.executeCatching {
                transactionService.addSpendingTransaction(
                    AddTransactionRequest(
                        type = command.type.name,
                        amount = command.amount.toDouble(),
                        budgetId = command.budgetId
                    )
                )
            }.map { operationResult ->
                operationResult.mapToTransaction()
            }
        }
    }
}
