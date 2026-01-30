package com.example.animeflix.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AnimeImagesResponse(
    @SerializedName("jpg") var jpg: AnimeImageJpg? = AnimeImageJpg(),
    @SerializedName("webp") var webp: AnimeImageWebp? = AnimeImageWebp()
)

@Keep
data class AnimeImageJpg(
    @SerializedName("image_url") var imageUrl: String? = null
)

@Keep
data class AnimeImageWebp(
    @SerializedName("image_url") var imageUrl: String? = null
)
