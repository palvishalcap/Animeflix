package com.example.animeflix.data.model

sealed class NetworkStatus {
    object Available : NetworkStatus()
    object Unavailable : NetworkStatus()
    object Idle : NetworkStatus()
}
