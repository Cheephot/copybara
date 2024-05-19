package com.xakaton.wallet.domain.query.operations.users

import arrow.core.Either
import com.xakaton.wallet.data.remote.services.UsersService
import com.xakaton.wallet.di.IoDispatcher
import com.xakaton.wallet.domain.models.User
import com.xakaton.wallet.domain.models.User.Companion.mapToUser
import com.xakaton.wallet.domain.query.RemoteQuery
import com.xakaton.wallet.domain.query.RemoteQueryHandler
import com.xakaton.wallet.domain.utils.rest.RestUtils
import com.xakaton.wallet.domain.utils.rest.RestUtils.mapRight
import com.xakaton.wallet.domain.utils.rest.TechnicalError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

data object GetMeQuery : RemoteQuery<User>

class GetMeQueryHandler @Inject constructor(
    private val usersService: UsersService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : RemoteQueryHandler<GetMeQuery, User> {
    override fun handle(query: GetMeQuery): Flow<Either<TechnicalError, User>> {
        return RestUtils.toFlowCatching {
            usersService.getMe()
        }.mapRight { operationResult ->
            operationResult.mapToUser()
        }.flowOn(dispatcher)
    }
}