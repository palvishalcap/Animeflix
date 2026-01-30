package com.example.animeflix.domain

import androidx.paging.PagingData
import com.example.animeflix.data.datasource.ApiResult
import com.example.animeflix.data.db.AnimeWithGenres
import com.example.animeflix.data.db.AnimeEntity
import com.example.animeflix.data.db.CastWithCharacter
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {

    fun animeListPager(): Flow<PagingData<AnimeEntity>>

    fun observeAnime(animeId: Int): Flow<AnimeWithGenres?>

    fun observeCast(animeId: Int): Flow<List<CastWithCharacter>>

    suspend fun refreshAnimeDetail(animeId: Int): ApiResult<Unit>

    suspend fun refreshAnimeCast(animeId: Int): ApiResult<Unit>

}