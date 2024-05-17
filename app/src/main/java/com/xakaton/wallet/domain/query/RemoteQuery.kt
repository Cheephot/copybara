package com.xakaton.wallet.domain.query

import arrow.core.Either
import com.xakaton.wallet.domain.utils.rest.TechnicalError

typealias RemoteQuery<RESULT> = Query<@JvmSuppressWildcards Either<TechnicalError, RESULT>>

