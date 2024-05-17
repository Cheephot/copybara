package com.xakaton.wallet.domain.query

import com.xakaton.wallet.domain.utils.network.NetworkStatusTracker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QueryDispatcher @Inject constructor(
    private val networkStatusTracker: NetworkStatusTracker,
    private val queryHandlers: Map<Class<out Query<*>>, @JvmSuppressWildcards QueryHandler<*, *>>,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Suppress("UNCHECKED_CAST")
    fun <QUERY : Query<RESULT>, RESULT> dispatch(query: QUERY): Flow<RESULT> {
        val queryHandlers = checkNotNull(
            queryHandlers[query::class.java] as? QueryHandler<QUERY, RESULT>
        ) {
            "Query handler for $query is not provided"
        }

        return networkStatusTracker.networkStatus.flatMapLatest { queryHandlers.handle(query) }
    }
}