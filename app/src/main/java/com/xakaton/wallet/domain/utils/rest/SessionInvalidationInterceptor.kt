package com.xakaton.wallet.domain.utils.rest

import com.xakaton.wallet.domain.command.CommandDispatcher
import com.xakaton.wallet.domain.command.operations.auth.LogOutCommand
import com.xakaton.wallet.domain.events.SessionInvalidationEvents
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class SessionInvalidationInterceptor @Inject constructor(
    private val commandDispatcher: Lazy<CommandDispatcher>,
    private val sessionInvalidationEvents: SessionInvalidationEvents,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        chain.proceed(chain.request()).also { response ->
            if (response.code == 401 && response.request.header("Authorization") != null) {
                commandDispatcher.get().dispatch(LogOutCommand)
                sessionInvalidationEvents.onSessionInvalidated()
            }
        }
    }
}
