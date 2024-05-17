package com.xakaton.wallet.domain.utils.rest

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException

object RestUtils {

    fun <A, B, C> Flow<Either<A, B>>.mapRight(block: suspend (B) -> C): Flow<Either<A, C>> {
        return map { either -> either.map { block(it) } }
    }

    fun <D> toFlowCatching(requestFunc: suspend () -> D): Flow<Either<TechnicalError, D>> =
        flow<Either<TechnicalError, D>> {
            emit(requestFunc().right())
        }.catch { throwable ->
            emit(throwable.toTechnicalError().left())
        }

    suspend fun <D> executeCatching(requestFunc: suspend () -> D): Either<TechnicalError, D> =
        try {
            requestFunc().right()
        } catch (exception: Exception) {
            exception.toTechnicalError().left()
        }

    private fun Throwable.toTechnicalError(): TechnicalError = when (this) {
        is HttpException -> {
            TechnicalError.HttpError(
                statusCode = response()?.code() ?: 404,
                message = response()?.errorBody()?.string() ?: "Unknown HTTP error"
            )
        }

        is IOException -> TechnicalError.NetworkError(this)

        else -> TechnicalError.ExceptionError(this as Exception)
    }
}