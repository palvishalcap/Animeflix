package com.example.animeflix.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.animeflix.data.api.AnimeApiClient
import com.example.animeflix.data.db.AnimeDatabase
import com.example.animeflix.data.db.AnimeEntity
import com.example.animeflix.data.db.AnimeWithGenres
import com.example.animeflix.data.db.CastWithCharacter
import com.example.animeflix.domain.AnimeRepository
import com.example.animeflix.utils.AnimeUtils
import kotlinx.coroutines.flow.Flow
import androidx.room.withTransaction
import javax.inject.Inject

class AnimeRepoImpl @Inject constructor(
    private val animeApiClient: AnimeApiClient,
    private val db: AnimeDatabase,
) : AnimeRepository{

    @OptIn(ExperimentalPagingApi::class)
    override fun animeListPager(): Flow<PagingData<AnimeEntity>> {
        val pagingSourceFactory = { db.animeDao().animePagingSource() }
        return Pager(
            config = PagingConfig(
                pageSize = AnimeUtils.ANIME_LIST_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            remoteMediator = AnimeListRemoteMediator(
                api = animeApiClient,
                db = db,
            ),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }

    override fun observeAnime(animeId: Int): Flow<AnimeWithGenres?> {
        return db.animeDao().observeAnimeWithGenres(animeId)
    }

    override fun observeCast(animeId: Int): Flow<List<CastWithCharacter>> {
        return db.animeDao().observeCast(animeId)
    }

    override suspend fun refreshAnimeDetail(animeId: Int): ApiResult<Unit> {
        return try {
            val response = animeApiClient.getAnimeDetail(animeId)
            val now = System.currentTimeMillis()

            val anime = response.data
            val stableOrder = db.animeDao().getAnimeListOrder(animeId) ?: Int.MAX_VALUE

            val animeEntity = anime.toAnimeEntity(now = now, listOrder = stableOrder)

            val genres = anime.genres.mapNotNull { it.toGenreEntityOrNull() }.distinctBy { it.genreId }
            val refs = anime.genres.mapNotNull { it.toAnimeGenreCrossRefOrNull(animeId) }

            db.withTransaction {
                db.animeDao().upsertAnime(listOf(animeEntity))
                db.animeDao().upsertGenres(genres)
                db.animeDao().deleteAnimeGenresForAnime(animeId)
                db.animeDao().upsertAnimeGenres(refs)
            }

            ApiResult.Success(Unit)
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
    }

    override suspend fun refreshAnimeCast(animeId: Int): ApiResult<Unit> {
        return try {
            val response = animeApiClient.getAnimeCharacters(animeId)

            val characters = response.data.mapNotNull { it.toCharacterEntityOrNull() }
            val cast = response.data.mapIndexedNotNull { index, item ->
                item.toAnimeCastEntityOrNull(animeId = animeId, orderIndex = index)
            }

            db.withTransaction {
                db.animeDao().upsertCharacters(characters)
                db.animeDao().deleteCastForAnime(animeId)
                db.animeDao().upsertAnimeCast(cast)
            }

            ApiResult.Success(Unit)
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
    }
}
