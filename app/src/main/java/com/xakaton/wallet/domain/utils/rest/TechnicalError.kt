package com.xakaton.wallet.domain.utils.rest

sealed class TechnicalError {

    data class ExceptionError(
        val exception: Exception
    ) : TechnicalError()

    data class HttpError(
        val statusCode: Int,
        val message: String
    ) : TechnicalError()

    data class NetworkError(
        val exception: Exception
    ) : TechnicalError()
}