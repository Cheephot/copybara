package com.xakaton.wallet.di

import com.squareup.moshi.Moshi
import com.xakaton.wallet.data.remote.services.AuthService
import com.xakaton.wallet.BuildConfig
import com.xakaton.wallet.data.remote.services.GoalsService
import com.xakaton.wallet.data.remote.services.TransactionService
import com.xakaton.wallet.data.remote.services.UsersService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class UnauthenticatedAuthService

@Module
@InstallIn(SingletonComponent::class)
object RemoteServicesModule {

    @Provides
    @Singleton
    @UnauthenticatedAuthService
    fun provideUnauthenticatedAuthService(moshi: Moshi): AuthService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(AuthService::class.java)
    }


    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideUsersService(retrofit: Retrofit): UsersService {
        return retrofit.create(UsersService::class.java)
    }

    @Provides
    @Singleton
    fun provideTransactionService(retrofit: Retrofit): TransactionService {
        return retrofit.create(TransactionService::class.java)
    }

    @Provides
    @Singleton
    fun provideGoalsService(retrofit: Retrofit): GoalsService {
        return retrofit.create(GoalsService::class.java)
    }
}