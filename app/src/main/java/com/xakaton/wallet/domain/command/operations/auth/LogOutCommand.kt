package com.xakaton.wallet.domain.command.operations.auth

import com.xakaton.wallet.data.local.dataStore.SessionDataStore
import com.xakaton.wallet.domain.command.Command
import com.xakaton.wallet.domain.command.CommandHandler
import com.xakaton.wallet.domain.models.SessionData
import javax.inject.Inject

data object LogOutCommand : Command<Unit>

class LogOutCommandHandler @Inject constructor(
    private val sessionDataStore: SessionDataStore,
) : CommandHandler<LogOutCommand, Unit> {

    override suspend fun handle(command: LogOutCommand) {
        sessionDataStore.updateData { SessionData(null, null) }
    }
}