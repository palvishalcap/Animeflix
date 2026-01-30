package com.example.animeflix.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM remote_keys WHERE animeId = :animeId")
    suspend fun remoteKeysAnimeId(animeId: Int): RemoteKeysEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeysEntity>)

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}
