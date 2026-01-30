package com.example.animeflix.utils

object AnimeUtils {

    const val ANIME_LIST_PAGE_SIZE = 20

    fun getVideoId(url: String?): String? {
       return url?.run {
             when {
                url.contains("youtube.com/watch?v=") -> {
                    url.substringAfter("v=").substringBefore("&")
                }
                url.contains("youtu.be/") -> {
                    url.substringAfter("youtu.be/").substringBefore("?")
                }
                url.contains("/embed/") -> {
                    url.substringAfter("/embed/").substringBefore("?")
                }
                else -> null
            }
        }
    }
}