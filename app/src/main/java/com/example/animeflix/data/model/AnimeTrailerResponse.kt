package com.example.animeflix.data.model

import com.google.gson.annotations.SerializedName

data class AnimeTrailerResponse(
    @SerializedName("youtube_id") var youtubeId: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("embed_url") var embedUrl: String? = null,
    @SerializedName("images") var images: AnimeImagesResponse? = AnimeImagesResponse()
)
