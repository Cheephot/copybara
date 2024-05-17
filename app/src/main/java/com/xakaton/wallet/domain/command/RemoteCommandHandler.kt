package com.xakaton.wallet.domain.command

import arrow.core.Either
import com.xakaton.wallet.domain.utils.rest.TechnicalError

typealias RemoteCommandHandler<COMMAND, RESULT> = CommandHandler<COMMAND, @JvmSuppressWildcards Either<TechnicalError, RESULT>>