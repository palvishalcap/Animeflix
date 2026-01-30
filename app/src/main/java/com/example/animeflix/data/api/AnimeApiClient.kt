package com.example.animeflix.data.api

import com.example.animeflix.data.model.AnimeCharactersApiResponse
import com.example.animeflix.data.model.AnimeDetailApiResponse
import com.example.animeflix.data.model.AnimeListAPIResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApiClient {

    @GET("v4/top/anime")
    suspend fun getAnimeList( @Query("page") page: Int, @Query("count") pageCount: Int): AnimeListAPIResponse

    @GET("v4/anime/{animeId}")
    suspend fun getAnimeDetail(@Path ("animeId") animeId: Int): AnimeDetailApiResponse

    @GET("v4/anime/{animeId}/characters")
    suspend fun getAnimeCharacters(@Path ("animeId") animeId: Int) : AnimeCharactersApiResponse
}