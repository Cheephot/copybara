package com.xakaton.wallet.domain.query

import arrow.core.Either
import com.xakaton.wallet.domain.utils.rest.TechnicalError

typealias RemoteQueryHandler<COMMAND, RESULT> = QueryHandler<COMMAND, @JvmSuppressWildcards Either<TechnicalError, RESULT>>