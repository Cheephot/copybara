package com.xakaton.wallet.domain.command

fun interface CommandHandler<COMMAND : Command<RESULT>, RESULT> {

    suspend fun handle(command: COMMAND): RESULT
}