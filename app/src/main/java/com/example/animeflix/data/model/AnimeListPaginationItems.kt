package com.example.animeflix.data.model

import com.google.gson.annotations.SerializedName

data class AnimeListPaginationItems(
    @SerializedName("count") var count: Int? = null,
    @SerializedName("total") var total: Int? = null,
    @SerializedName("per_page") var perPage: Int? = null
)