package com.xakaton.wallet.domain.command

import arrow.core.Either
import com.xakaton.wallet.domain.utils.rest.TechnicalError

typealias RemoteCommand<RESULT> = Command<@JvmSuppressWildcards Either<TechnicalError, RESULT>>