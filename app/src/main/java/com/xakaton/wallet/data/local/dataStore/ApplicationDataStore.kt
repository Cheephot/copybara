package com.xakaton.wallet.data.local.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

abstract class ApplicationDataStore<T>(
    @ApplicationContext private val context: Context,
    private val moshi: Moshi,
    private val key: Preferences.Key<String>,
    private val type: Class<T>,
    private val defaultValue: T,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    fileName: String
) : DataStore<T> {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = fileName)

    private val mutex = Mutex()

    private fun serialize(value: T): String {
        val jsonAdapter = moshi.adapter(type)
        return jsonAdapter.toJson(value)
    }

    private fun deserialize(string: String?): T {
        val jsonAdapter = moshi.adapter(type)
        return string?.let { jsonAdapter.fromJson(it) } ?: defaultValue
    }

    override val data: Flow<T>
        get() = context.dataStore.data.map { preferences ->
            preferences[key]?.let { deserialize(it) } ?: defaultValue
        }.flowOn(dispatcher)

    override suspend fun updateData(transform: suspend (t: T) -> T): T {
        return mutex.withLock {
            withContext(dispatcher) {
                try {
                    context.dataStore.edit { preferences ->
                        val current = preferences[key]?.let { deserialize(it) } ?: defaultValue
                        val updated = transform(current)
                        preferences[key] = serialize(updated)
                    }

                    context.dataStore.data.first()[key]?.let { deserialize(it) } ?: defaultValue

                } catch (ex: Exception) {
                    defaultValue
                }
            }
        }
    }
}