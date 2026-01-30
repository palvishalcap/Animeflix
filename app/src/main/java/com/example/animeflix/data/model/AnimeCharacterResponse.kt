package com.example.animeflix.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AnimeCharacterResponse(
    @SerializedName("mal_id") var malId: Int? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("images") var images: AnimeImagesResponse? = AnimeImagesResponse(),
    @SerializedName("name") var name: String? = null
)
