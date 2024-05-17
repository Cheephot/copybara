package com.xakaton.wallet.domain.utils.rest

import com.xakaton.wallet.data.local.dataStore.SessionDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationInterceptor @Inject constructor(
    private val sessionDataStore: SessionDataStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return runBlocking {
            val originalRequest = chain.request()

            if (originalRequest.url.encodedPath == "/api/users/auth/access_token/") {
                return@runBlocking chain.proceed(originalRequest)
            }

            val accessToken = sessionDataStore.data.firstOrNull()?.accessToken

            val newRequest = accessToken?.let {
                originalRequest.newBuilder()
                    .addHeader("Authorization", it)
                    .build()
            } ?: originalRequest

            return@runBlocking chain.proceed(newRequest)
        }
    }
}