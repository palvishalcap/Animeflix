package com.example.animeflix.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        AnimeEntity::class,
        GenreEntity::class,
        AnimeGenreCrossRef::class,
        CharacterEntity::class,
        AnimeCastEntity::class,
        RemoteKeysEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}
