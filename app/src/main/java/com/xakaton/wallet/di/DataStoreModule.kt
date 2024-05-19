package com.xakaton.wallet.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.xakaton.wallet.data.local.dataStore.SessionDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideSessionDataStore(
        @ApplicationContext context: Context,
        moshi: Moshi,
    ): SessionDataStore {
        return SessionDataStore(
            context = context,
            moshi = moshi
        )
    }
}