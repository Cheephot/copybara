package com.xakaton.wallet.domain.query.operations.transactions

import arrow.core.Either
import com.xakaton.wallet.data.remote.services.TransactionService
import com.xakaton.wallet.di.IoDispatcher
import com.xakaton.wallet.domain.models.Transaction
import com.xakaton.wallet.domain.models.Transaction.Companion.mapToTransaction
import com.xakaton.wallet.domain.query.RemoteQuery
import com.xakaton.wallet.domain.query.RemoteQueryHandler
import com.xakaton.wallet.domain.utils.rest.RestUtils
import com.xakaton.wallet.domain.utils.rest.RestUtils.mapRight
import com.xakaton.wallet.domain.utils.rest.TechnicalError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

data class GetIncomeTransactionsQuery(val budgetId: String) : RemoteQuery<List<Transaction>>

class GetIncomeTransactionsQueryHandler @Inject constructor(
    private val transactionService: TransactionService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : RemoteQueryHandler<GetIncomeTransactionsQuery, List<Transaction>> {
    override fun handle(query: GetIncomeTransactionsQuery): Flow<Either<TechnicalError, List<Transaction>>> {
        return RestUtils.toFlowCatching {
            transactionService.getIncomeTransactions(budgetId = query.budgetId)
        }.mapRight { operationResult ->
            operationResult.map { it.mapToTransaction() }
        }.flowOn(dispatcher)
    }
}
