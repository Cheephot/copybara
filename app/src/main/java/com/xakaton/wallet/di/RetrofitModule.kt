package com.xakaton.wallet.di

import com.squareup.moshi.Moshi
import com.xakaton.wallet.BuildConfig
import com.xakaton.wallet.domain.utils.rest.AuthenticationInterceptor
import com.xakaton.wallet.domain.utils.rest.Authenticator
import com.xakaton.wallet.domain.utils.rest.SessionInvalidationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        moshi: Moshi,
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun okHttpClient(
        authenticationInterceptor: AuthenticationInterceptor,
        authenticator: Authenticator,
        sessionInvalidationInterceptor: SessionInvalidationInterceptor,
    ): OkHttpClient {

        return OkHttpClient.Builder()
            .authenticator(authenticator)
            .addInterceptor(authenticationInterceptor)
            .addInterceptor(sessionInvalidationInterceptor)
            .build()
    }
}