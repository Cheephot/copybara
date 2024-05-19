package com.xakaton.wallet.domain.command.operations

import arrow.core.Either
import com.xakaton.wallet.data.remote.dto.AddGoalsRequest
import com.xakaton.wallet.data.remote.services.GoalsService
import com.xakaton.wallet.di.IoDispatcher
import com.xakaton.wallet.domain.command.RemoteCommand
import com.xakaton.wallet.domain.command.RemoteCommandHandler
import com.xakaton.wallet.domain.models.Goal
import com.xakaton.wallet.domain.models.Goal.Companion.mapToGoal
import com.xakaton.wallet.domain.utils.rest.RestUtils
import com.xakaton.wallet.domain.utils.rest.TechnicalError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import javax.inject.Inject

data class AddGoalsCommand(
    val userId: String,
    val name: String,
    val expectedAmount: BigDecimal,
) : RemoteCommand<Goal>

class AddGoalsCommandHandler @Inject constructor(
    private val goalsService: GoalsService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : RemoteCommandHandler<AddGoalsCommand, Goal> {
    override suspend fun handle(command: AddGoalsCommand): Either<TechnicalError, Goal> {
        return withContext(dispatcher) {
            RestUtils.executeCatching {
                goalsService.addGoal(
                    userId = command.userId,
                    AddGoalsRequest(
                        name = command.name,
                        expectedAmount = command.expectedAmount.toDouble()
                    )
                )
            }.map { operationResult ->
                operationResult.mapToGoal()
            }
        }
    }
}
