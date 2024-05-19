package com.xakaton.wallet.domain.query.operations

import com.xakaton.wallet.data.local.dataStore.SessionDataStore
import com.xakaton.wallet.domain.models.SessionData
import com.xakaton.wallet.domain.query.Query
import com.xakaton.wallet.domain.query.QueryHandler
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data object GetSessionDataQuery : Query<SessionData?>

class GetSessionDataQueryHandler @Inject constructor(
    private val sessionDataStore: SessionDataStore,
) : QueryHandler<GetSessionDataQuery, SessionData?> {

    override fun handle(query: GetSessionDataQuery): Flow<SessionData?> {
        return sessionDataStore.data
    }
}