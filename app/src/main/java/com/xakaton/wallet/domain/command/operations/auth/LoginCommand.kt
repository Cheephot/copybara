package com.xakaton.wallet.domain.command.operations.auth

import arrow.core.Either
import com.xakaton.wallet.data.local.dataStore.SessionDataStore
import com.xakaton.wallet.data.remote.dto.LoginDto
import com.xakaton.wallet.data.remote.services.AuthService
import com.xakaton.wallet.di.IoDispatcher
import com.xakaton.wallet.domain.command.RemoteCommand
import com.xakaton.wallet.domain.command.RemoteCommandHandler
import com.xakaton.wallet.domain.models.SessionData
import com.xakaton.wallet.domain.utils.rest.RestUtils
import com.xakaton.wallet.domain.utils.rest.TechnicalError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class LoginCommand(
    val email: String,
    val password: String,
) : RemoteCommand<Unit>

class LoginCommandHandler @Inject constructor(
    private val authService: AuthService,
    private val sessionDataStore: SessionDataStore,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : RemoteCommandHandler<LoginCommand, Unit> {
    override suspend fun handle(command: LoginCommand): Either<TechnicalError, Unit> {
        return withContext(dispatcher) {
            RestUtils.executeCatching {
                authService.login(
                    LoginDto(
                        email = command.email,
                        password = command.password
                    )
                )
            }.map { operationResult ->
                sessionDataStore.updateData {
                    SessionData(
                        accessToken = operationResult.accessToken,
                        refreshToken = operationResult.refreshToken
                    )
                }

                Unit
            }
        }
    }
}
