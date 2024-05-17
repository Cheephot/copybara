package com.xakaton.wallet.di

import com.xakaton.wallet.domain.query.Query
import com.xakaton.wallet.domain.query.QueryHandler
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
}