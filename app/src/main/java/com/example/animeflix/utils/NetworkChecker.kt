package com.example.animeflix.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import com.example.animeflix.data.model.NetworkStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NetworkChecker(
    context: Context
) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _networkStatus = MutableStateFlow<NetworkStatus>(NetworkStatus.Idle)
    val networkStatus: StateFlow<NetworkStatus> = _networkStatus

    private val callback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            _networkStatus.value = NetworkStatus.Available
        }

        override fun onLost(network: Network) {
            _networkStatus.value = NetworkStatus.Unavailable
        }

        override fun onUnavailable() {
            _networkStatus.value = NetworkStatus.Unavailable
        }
    }

    fun register() {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, callback)
        Log.d("NetworkAnime","register called")
    }

    fun unregister() {
        runCatching {
            connectivityManager.unregisterNetworkCallback(callback)
            Log.d("NetworkAnime","un register called")
        }
    }

}
