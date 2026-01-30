package com.example.animeflix.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AnimeListDataResponse(
    @SerializedName("mal_id") var malId: Int? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("images") var images: AnimeImagesResponse? = AnimeImagesResponse(),
    @SerializedName("approved") var approved: Boolean? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("episodes") var episodes: Int? = null,
    @SerializedName("duration") var duration: String? = null,
    @SerializedName("rating") var rating: String? = null,
    @SerializedName("score") var score: Double? = null,
    @SerializedName("rank") var rank: Int? = null,
    @SerializedName("synopsis") var synopsis: String? = null,
    @SerializedName("trailer") var trailer: AnimeTrailerResponse? = AnimeTrailerResponse(),
    @SerializedName("genres") var genres: ArrayList<AnimeGenreResponse> = arrayListOf(),
)
