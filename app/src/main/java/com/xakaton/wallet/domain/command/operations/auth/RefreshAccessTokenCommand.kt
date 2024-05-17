package com.xakaton.wallet.domain.command.operations.auth

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.xakaton.wallet.data.local.dataStore.SessionDataStore
import com.xakaton.wallet.data.remote.services.AuthService
import com.xakaton.wallet.di.IoDispatcher
import com.xakaton.wallet.di.UnauthenticatedAuthService
import com.xakaton.wallet.domain.command.RemoteCommand
import com.xakaton.wallet.domain.command.RemoteCommandHandler
import com.xakaton.wallet.domain.models.SessionData
import com.xakaton.wallet.domain.utils.rest.RestUtils.executeCatching
import com.xakaton.wallet.domain.utils.rest.TechnicalError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class RefreshAccessTokenCommand(
    val accessToken: String?
) : RemoteCommand<String>

class RefreshAccessTokenCommandHandlerImpl @Inject constructor(
    @UnauthenticatedAuthService private val authService: AuthService,
    private val sessionDataStore: SessionDataStore,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : RemoteCommandHandler<RefreshAccessTokenCommand, String> {

    private val mutex = Mutex()

    override suspend fun handle(command: RefreshAccessTokenCommand): Either<TechnicalError, String> {
        return withContext(dispatcher) {
            mutex.withLock {
                val session = sessionDataStore.data.firstOrNull()

                if (session?.refreshToken == null) {
                    return@withLock TechnicalError.HttpError(401, "Session not found").left()
                }

                if (command.accessToken != session.accessToken && session.accessToken != null) {
                    return@withLock session.accessToken.right()
                }

                executeCatching {
                    authService
                        .refreshAccessToken(refreshToken = session.refreshToken)
                }.map { operationResult ->
                    sessionDataStore.updateData {
                        SessionData(
                            accessToken = operationResult.accessToken,
                            refreshToken = operationResult.refreshToken
                        )
                    }

                    operationResult.accessToken
                }
            }
        }
    }
}