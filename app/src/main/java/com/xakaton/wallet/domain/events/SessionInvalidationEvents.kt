package com.xakaton.wallet.domain.events

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionInvalidationEvents @Inject constructor() {

    private val channel = Channel<Unit>(capacity = Channel.CONFLATED)

    fun events() = channel.receiveAsFlow()

    fun onSessionInvalidated() {
        channel.trySend(Unit)
    }
}
