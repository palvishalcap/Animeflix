package com.example.animeflix.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AnimeDetailApiResponse(
    @SerializedName("data") var data: AnimeListDataResponse
)
