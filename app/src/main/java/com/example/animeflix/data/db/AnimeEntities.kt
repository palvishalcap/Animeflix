package com.example.animeflix.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime")
data class AnimeEntity(
    @PrimaryKey
    val animeId: Int,
    val title: String,
    val imageUrl: String?,
    val score: Double?,
    val episodes: Int?,
    val synopsis: String?,
    val trailerYoutubeId: String?,
    val updatedAt: Long,
    val listOrder: Int,
)

@Entity(tableName = "genre")
data class GenreEntity(
    @PrimaryKey
    val genreId: Int,
    val name: String,
)

@Entity(
    tableName = "anime_genre",
    primaryKeys = ["animeId", "genreId"],
)
data class AnimeGenreCrossRef(
    val animeId: Int,
    val genreId: Int,
)

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey
    val characterId: Int,
    val name: String,
    val imageUrl: String?,
)

@Entity(
    tableName = "anime_cast",
    primaryKeys = ["animeId", "characterId"],
)
data class AnimeCastEntity(
    val animeId: Int,
    val characterId: Int,
    val role: String?,
    val favorites: Int?,
    val orderIndex: Int,
)

@Entity(tableName = "remote_keys")
data class RemoteKeysEntity(
    @PrimaryKey
    val animeId: Int,
    val prevKey: Int?,
    val nextKey: Int?,
)
