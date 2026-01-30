package com.example.animeflix.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.animeflix.data.api.AnimeApiClient
import com.example.animeflix.data.db.AnimeDatabase
import com.example.animeflix.data.db.AnimeEntity
import com.example.animeflix.data.db.AnimeGenreCrossRef
import com.example.animeflix.data.db.GenreEntity
import com.example.animeflix.data.db.RemoteKeysEntity
import com.example.animeflix.utils.AnimeUtils

@OptIn(ExperimentalPagingApi::class)
class AnimeListRemoteMediator(
    private val api: AnimeApiClient,
    private val db: AnimeDatabase,
) : RemoteMediator<Int, AnimeEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeEntity>,
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    val remoteKeys = db.remoteKeysDao().remoteKeysAnimeId(lastItem.animeId)
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    remoteKeys.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val response = api.getAnimeList(page, AnimeUtils.ANIME_LIST_PAGE_SIZE)
            val items = response.data
            val endOfPaginationReached = response.pagination?.hasNextPage == false || items.isEmpty()

            val now = System.currentTimeMillis()
            val baseOrder = (page - 1) * state.config.pageSize

            val animeEntities = items.mapIndexed { index, anime ->
                AnimeEntity(
                    animeId = anime.malId ?: -1,
                    title = anime.title.orEmpty(),
                    imageUrl = anime.images?.jpg?.imageUrl,
                    score = anime.score,
                    episodes = anime.episodes,
                    synopsis = anime.synopsis,
                    trailerYoutubeId = anime.trailer?.youtubeId,
                    updatedAt = now,
                    listOrder = baseOrder + index,
                )
            }.filter { it.animeId != -1 }

            val genreEntities = items.flatMap { anime ->
                anime.genres.mapNotNull { genre ->
                    val id = genre.malId
                    val name = genre.name
                    if (id == null || name.isNullOrBlank()) null else GenreEntity(id, name)
                }
            }.distinctBy { it.genreId }

            val animeGenreRefs = items.flatMap { anime ->
                val animeId = anime.malId
                if (animeId == null) return@flatMap emptyList()
                anime.genres.mapNotNull { genre ->
                    val gid = genre.malId
                    if (gid == null) null else AnimeGenreCrossRef(animeId, gid)
                }
            }

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1
            val remoteKeys = animeEntities.map {
                RemoteKeysEntity(animeId = it.animeId, prevKey = prevKey, nextKey = nextKey)
            }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeysDao().clearRemoteKeys()
                    db.animeDao().clearAnimeGenres()
                    db.animeDao().clearGenres()
                    db.animeDao().clearAnime()
                }

                db.animeDao().upsertAnime(animeEntities)
                db.animeDao().upsertGenres(genreEntities)
                db.animeDao().upsertAnimeGenres(animeGenreRefs)
                db.remoteKeysDao().insertAll(remoteKeys)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
