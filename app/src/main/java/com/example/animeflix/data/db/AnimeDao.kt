package com.example.animeflix.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    @Query("SELECT * FROM anime ORDER BY listOrder ASC")
    fun animePagingSource(): PagingSource<Int, AnimeEntity>

    @Transaction
    @Query("SELECT * FROM anime WHERE animeId = :animeId LIMIT 1")
    fun observeAnimeWithGenres(animeId: Int): Flow<AnimeWithGenres?>

    @Transaction
    @Query("SELECT * FROM anime_cast WHERE animeId = :animeId ORDER BY orderIndex ASC")
    fun observeCast(animeId: Int): Flow<List<CastWithCharacter>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAnime(items: List<AnimeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertGenres(items: List<GenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAnimeGenres(items: List<AnimeGenreCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCharacters(items: List<CharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAnimeCast(items: List<AnimeCastEntity>)

    @Query("DELETE FROM anime")
    suspend fun clearAnime()

    @Query("DELETE FROM genre")
    suspend fun clearGenres()

    @Query("DELETE FROM anime_genre")
    suspend fun clearAnimeGenres()

    @Query("DELETE FROM characters")
    suspend fun clearCharacters()

    @Query("DELETE FROM anime_cast")
    suspend fun clearAnimeCast()

    @Query("DELETE FROM anime_genre WHERE animeId = :animeId")
    suspend fun deleteAnimeGenresForAnime(animeId: Int)

    @Query("DELETE FROM anime_cast WHERE animeId = :animeId")
    suspend fun deleteCastForAnime(animeId: Int)

    @Query("SELECT listOrder FROM anime WHERE animeId = :animeId LIMIT 1")
    suspend fun getAnimeListOrder(animeId: Int): Int?
}
