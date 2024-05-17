package com.xakaton.wallet.domain.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.getSystemService
import com.xakaton.wallet.di.ApplicationScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkStatusTracker @Inject constructor(
    @ApplicationContext context: Context,
    @ApplicationScope coroutineScope: CoroutineScope,
) {
    private val connectivityManager = context.getSystemService<ConnectivityManager>()

    val networkStatus = callbackFlow {
        val networkStatusCallback = object : ConnectivityManager.NetworkCallback() {

            private val networks = hashSetOf<Network>()

            override fun onAvailable(network: Network) {
                networks += network
                trySend(NetworkStatus.AVAILABLE)
            }

            override fun onLost(network: Network) {
                networks -= network

                channel.trySend(
                    when {
                        networks.isEmpty() -> NetworkStatus.UNAVAILABLE
                        else -> NetworkStatus.AVAILABLE
                    }
                )
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager?.registerNetworkCallback(request, networkStatusCallback)

        trySend(getNetworkStatus())

        awaitClose { connectivityManager?.unregisterNetworkCallback(networkStatusCallback) }
    }
        .stateIn(coroutineScope, SharingStarted.Eagerly, initialValue = getNetworkStatus())

    private fun getNetworkStatus(): NetworkStatus {
        return when (
            connectivityManager
                ?.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        ) {
            true -> NetworkStatus.AVAILABLE
            false, null -> NetworkStatus.UNAVAILABLE
        }
    }
}