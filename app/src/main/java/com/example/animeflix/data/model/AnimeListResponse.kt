package com.example.animeflix.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AnimeListAPIResponse(
    @SerializedName("pagination") var pagination: AnimeListPagination? = AnimeListPagination(),
    @SerializedName("data") var data: ArrayList<AnimeListDataResponse> = arrayListOf()
)
