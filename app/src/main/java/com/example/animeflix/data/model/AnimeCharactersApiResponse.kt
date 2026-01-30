package com.example.animeflix.data.model

import com.google.gson.annotations.SerializedName

data class AnimeCharactersApiResponse(
    @SerializedName("data") var data: ArrayList<AnimeCharacterItemResponse> = arrayListOf(),
)

data class AnimeCharacterItemResponse(
    @SerializedName("character") var character: AnimeCharacterResponse? = AnimeCharacterResponse(),
    @SerializedName("role") var role: String? = null,
    @SerializedName("favorites") var favorites: Int? = null,
)
