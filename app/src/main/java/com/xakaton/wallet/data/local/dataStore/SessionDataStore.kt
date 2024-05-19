package com.xakaton.wallet.data.local.dataStore

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import com.squareup.moshi.Moshi
import com.xakaton.wallet.di.IoDispatcher
import com.xakaton.wallet.domain.models.SessionData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class SessionDataStore(
    @ApplicationContext private val context: Context,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    moshi: Moshi,
) : ApplicationDataStore<SessionData>(
    context = context,
    moshi = moshi,
    key = stringPreferencesKey("session_key"),
    type = SessionData::class.java,
    defaultValue = SessionData(),
    dispatcher = dispatcher,
    fileName = "session_prefs"
)