package com.xakaton.wallet.domain.command.operations.auth

import arrow.core.Either
import com.xakaton.wallet.data.local.dataStore.SessionDataStore
import com.xakaton.wallet.data.remote.dto.RegisterDto
import com.xakaton.wallet.data.remote.services.AuthService
import com.xakaton.wallet.di.IoDispatcher
import com.xakaton.wallet.domain.command.RemoteCommand
import com.xakaton.wallet.domain.command.RemoteCommandHandler
import com.xakaton.wallet.domain.models.SessionData
import com.xakaton.wallet.domain.utils.rest.RestUtils.executeCatching
import com.xakaton.wallet.domain.utils.rest.TechnicalError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class RegisterCommand(
    val name: String,
    val email: String,
    val password: String,
) : RemoteCommand<Unit>

class RegisterCommandHandler @Inject constructor(
    private val authService: AuthService,
    private val sessionDataStore: SessionDataStore,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : RemoteCommandHandler<RegisterCommand, Unit> {
    override suspend fun handle(command: RegisterCommand): Either<TechnicalError, Unit> {
        return withContext(dispatcher) {
            executeCatching {
                authService.register(
                    RegisterDto(
                        name = command.name,
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


