package com.xakaton.wallet.di

import com.xakaton.wallet.domain.command.Command
import com.xakaton.wallet.domain.command.CommandHandler
import com.xakaton.wallet.domain.command.operations.AddGoalsCommand
import com.xakaton.wallet.domain.command.operations.AddGoalsCommandHandler
import com.xakaton.wallet.domain.command.operations.auth.LogOutCommand
import com.xakaton.wallet.domain.command.operations.auth.LogOutCommandHandler
import com.xakaton.wallet.domain.command.operations.auth.LoginCommand
import com.xakaton.wallet.domain.command.operations.auth.LoginCommandHandler
import com.xakaton.wallet.domain.command.operations.auth.RegisterCommand
import com.xakaton.wallet.domain.command.operations.auth.RegisterCommandHandler
import com.xakaton.wallet.domain.command.operations.transactions.AddIncomeTransactionCommand
import com.xakaton.wallet.domain.command.operations.transactions.AddIncomeTransactionCommandHandler
import com.xakaton.wallet.domain.command.operations.transactions.AddSpendingTransactionCommand
import com.xakaton.wallet.domain.command.operations.transactions.AddSpendingTransactionCommandHandler
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
        handler: RegisterCommandHandler,
    ): CommandHandler<*, *>

    @Binds
    @IntoMap
    @CommandHandlerKey(LoginCommand::class)
    abstract fun bindLoginCommandHandler(
        handler: LoginCommandHandler,
    ): CommandHandler<*, *>

    @Binds
    @IntoMap
    @CommandHandlerKey(LogOutCommand::class)
    abstract fun bindLogOutCommandHandler(
        handler: LogOutCommandHandler,
    ): CommandHandler<*, *>

    @Binds
    @IntoMap
    @CommandHandlerKey(AddSpendingTransactionCommand::class)
    abstract fun bindAddSpendingTransactionCommandHandler(
        handler: AddSpendingTransactionCommandHandler,
    ): CommandHandler<*, *>

    @Binds
    @IntoMap
    @CommandHandlerKey(AddIncomeTransactionCommand::class)
    abstract fun bindAddIncomeTransactionCommandHandler(
        handler: AddIncomeTransactionCommandHandler,
    ): CommandHandler<*, *>

    @Binds
    @IntoMap
    @CommandHandlerKey(AddGoalsCommand::class)
    abstract fun bindAddGoalsCommandHandler(
        handler: AddGoalsCommandHandler,
    ): CommandHandler<*, *>
}