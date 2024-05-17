package com.xakaton.wallet.domain.utils.rest

import arrow.core.Either
import com.xakaton.wallet.domain.command.operations.auth.RefreshAccessTokenCommand
import com.xakaton.wallet.domain.command.operations.auth.RefreshAccessTokenCommandHandlerImpl
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Authenticator @Inject constructor(
    private val refreshAccessTokenCommandHandler: RefreshAccessTokenCommandHandlerImpl,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.count() > 3) return null

        return runBlocking {
            val remoteCommandCallResult = refreshAccessTokenCommandHandler.handle(
                RefreshAccessTokenCommand(
                    accessToken = response.request.header("Authorization")
                )
            )

            when (remoteCommandCallResult) {
                is Either.Left ->
                    null

                is Either.Right -> {
                    response.request.newBuilder()
                        .header("Authorization", remoteCommandCallResult.value)
                        .build()
                }
            }
        }
    }

    private tailrec fun Response.count(accumulator: Int = 0): Int {
        return if (priorResponse == null) accumulator + 1 else priorResponse!!.count(accumulator + 1)
    }
}


