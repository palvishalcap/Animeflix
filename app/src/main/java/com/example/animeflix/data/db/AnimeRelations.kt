package com.example.animeflix.data.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class AnimeWithGenres(
    @Embedded
    val anime: AnimeEntity,
    @Relation(
        parentColumn = "animeId",
        entityColumn = "genreId",
        associateBy = Junction(AnimeGenreCrossRef::class),
    )
    val genres: List<GenreEntity>,
)

data class CastWithCharacter(
    @Embedded
    val cast: AnimeCastEntity,
    @Relation(
        parentColumn = "characterId",
        entityColumn = "characterId",
    )
    val character: CharacterEntity,
)
