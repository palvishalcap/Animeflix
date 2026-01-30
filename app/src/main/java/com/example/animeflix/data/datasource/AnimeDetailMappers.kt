package com.example.animeflix.data.datasource

import com.example.animeflix.data.db.AnimeCastEntity
import com.example.animeflix.data.db.AnimeEntity
import com.example.animeflix.data.db.AnimeGenreCrossRef
import com.example.animeflix.data.db.CharacterEntity
import com.example.animeflix.data.db.GenreEntity
import com.example.animeflix.data.model.AnimeCharacterItemResponse
import com.example.animeflix.data.model.AnimeGenreResponse
import com.example.animeflix.data.model.AnimeListDataResponse

fun AnimeListDataResponse.toAnimeEntity(now: Long, listOrder: Int = 0): AnimeEntity {
    return AnimeEntity(
        animeId = malId ?: -1,
        title = title.orEmpty(),
        imageUrl = images?.jpg?.imageUrl,
        score = score,
        episodes = episodes,
        synopsis = synopsis,
        trailerYoutubeId = trailer?.embedUrl,
        updatedAt = now,
        listOrder = listOrder,
    )
}

fun AnimeGenreResponse.toGenreEntityOrNull(): GenreEntity? {
    val id = malId ?: return null
    val nameValue = name ?: return null
    if (nameValue.isBlank()) return null
    return GenreEntity(genreId = id, name = nameValue)
}

fun AnimeGenreResponse.toAnimeGenreCrossRefOrNull(animeId: Int): AnimeGenreCrossRef? {
    val gid = malId ?: return null
    return AnimeGenreCrossRef(animeId = animeId, genreId = gid)
}

fun AnimeCharacterItemResponse.toCharacterEntityOrNull(): CharacterEntity? {
    val c = character ?: return null
    val id = c.malId ?: return null
    val nameValue = c.name ?: return null
    if (nameValue.isBlank()) return null
    return CharacterEntity(
        characterId = id,
        name = nameValue,
        imageUrl = c.images?.jpg?.imageUrl,
    )
}

fun AnimeCharacterItemResponse.toAnimeCastEntityOrNull(animeId: Int, orderIndex: Int): AnimeCastEntity? {
    val c = character ?: return null
    val id = c.malId ?: return null
    return AnimeCastEntity(
        animeId = animeId,
        characterId = id,
        role = role,
        favorites = favorites,
        orderIndex = orderIndex,
    )
}
