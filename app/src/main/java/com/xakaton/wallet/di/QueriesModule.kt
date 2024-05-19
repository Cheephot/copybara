package com.xakaton.wallet.di

import com.xakaton.wallet.domain.query.Query
import com.xakaton.wallet.domain.query.QueryHandler
import com.xakaton.wallet.domain.query.operations.GetGoalQuery
import com.xakaton.wallet.domain.query.operations.GetGoalQueryHandler
import com.xakaton.wallet.domain.query.operations.GetSessionDataQuery
import com.xakaton.wallet.domain.query.operations.GetSessionDataQueryHandler
import com.xakaton.wallet.domain.query.operations.transactions.GetIncomeTransactionsQueryHandler
import com.xakaton.wallet.domain.query.operations.transactions.GetIncomeTransactionsQuery
import com.xakaton.wallet.domain.query.operations.transactions.GetSpendingTransactionsQuery
import com.xakaton.wallet.domain.query.operations.transactions.GetSpendingTransactionsQueryHandler
import com.xakaton.wallet.domain.query.operations.users.GetMeQuery
import com.xakaton.wallet.domain.query.operations.users.GetMeQueryHandler
import com.xakaton.wallet.domain.query.operations.users.GetUserByIdQuery
import com.xakaton.wallet.domain.query.operations.users.GetUserByIdQueryHandler
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
@InstallIn(SingletonComponent::class)
abstract class QueriesModule {

    @MapKey
    annotation class QueryHandlerKey(val value: KClass<out Query<*>>)

    @Binds
    @IntoMap
    @QueryHandlerKey(GetUserByIdQuery::class)
    abstract fun bindGetUserByIdQueryHandler(
        handler: GetUserByIdQueryHandler,
    ): QueryHandler<*, *>

    @Binds
    @IntoMap
    @QueryHandlerKey(GetSessionDataQuery::class)
    abstract fun bindGetSessionDataQueryHandler(
        handler: GetSessionDataQueryHandler,
    ): QueryHandler<*, *>

    @Binds
    @IntoMap
    @QueryHandlerKey(GetMeQuery::class)
    abstract fun bindGetMeQueryHandler(
        handler: GetMeQueryHandler,
    ): QueryHandler<*, *>

    @Binds
    @IntoMap
    @QueryHandlerKey(GetSpendingTransactionsQuery::class)
    abstract fun bindGetSpendingTransactionsQueryHandler(
        handler: GetSpendingTransactionsQueryHandler,
    ): QueryHandler<*, *>

    @Binds
    @IntoMap
    @QueryHandlerKey(GetIncomeTransactionsQuery::class)
    abstract fun bindGetIncomeTransactionsQueryHandler(
        handler: GetIncomeTransactionsQueryHandler,
    ): QueryHandler<*, *>

    @Binds
    @IntoMap
    @QueryHandlerKey(GetGoalQuery::class)
    abstract fun bindGetGoalQueryHandler(
        handler: GetGoalQueryHandler,
    ): QueryHandler<*, *>
}