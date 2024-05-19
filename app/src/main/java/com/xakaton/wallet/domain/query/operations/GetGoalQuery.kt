package com.xakaton.wallet.domain.query.operations

import arrow.core.Either
import com.xakaton.wallet.data.remote.services.GoalsService
import com.xakaton.wallet.di.IoDispatcher
import com.xakaton.wallet.domain.models.Goal
import com.xakaton.wallet.domain.models.Goal.Companion.mapToGoal
import com.xakaton.wallet.domain.query.RemoteQuery
import com.xakaton.wallet.domain.query.RemoteQueryHandler
import com.xakaton.wallet.domain.utils.rest.RestUtils
import com.xakaton.wallet.domain.utils.rest.RestUtils.mapRight
import com.xakaton.wallet.domain.utils.rest.TechnicalError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

data class GetGoalQuery(val userId: String) : RemoteQuery<List<Goal>>

class GetGoalQueryHandler @Inject constructor(
    private val goalsService: GoalsService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : RemoteQueryHandler<GetGoalQuery, List<Goal>> {
    override fun handle(query: GetGoalQuery): Flow<Either<TechnicalError, List<Goal>>> {
        return RestUtils.toFlowCatching {
            goalsService.getGoals(userId = query.userId)
        }.mapRight { operationResult ->
            operationResult.map { it.mapToGoal() }
        }.flowOn(dispatcher)
    }
}
