package com.xakaton.wallet.di

import com.xakaton.wallet.domain.command.Command
import com.xakaton.wallet.domain.command.CommandHandler
import com.xakaton.wallet.domain.command.operations.auth.RegisterCommand
import com.xakaton.wallet.domain.command.operations.auth.RegisterCommandHandler
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
@InstallIn(SingletonComponent::class)
abstract class CommandsModule {

    @MapKey
    annotation class CommandHandlerKey(val value: KClass<out Command<*>>)

    @Binds
    @IntoMap
    @CommandHandlerKey(RegisterCommand::class)
    abstract fun bindRegisterCommandHandler(
        handler: RegisterCommandHandler
    ): CommandHandler<*, *>
}