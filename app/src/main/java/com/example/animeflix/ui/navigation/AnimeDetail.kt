package com.example.animeflix.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
data class AnimeDetail(val animeId: Int): NavDestination()
