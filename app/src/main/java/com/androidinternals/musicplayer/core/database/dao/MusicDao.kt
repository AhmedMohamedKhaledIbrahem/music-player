package com.androidinternals.musicplayer.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.androidinternals.musicplayer.core.database.entity.MusicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDao: BaseDao<MusicEntity> {
    @Query("SELECT * FROM songs ORDER BY title COLLATE NOCASE ASC")
    fun getAllSongs(): Flow<List<MusicEntity>>
    @Query("SELECT * FROM songs WHERE id = :mediaStoreId LIMIT 1")
    suspend fun getSongById(mediaStoreId: Long): MusicEntity?
    @Query("DELETE FROM songs WHERE id NOT IN (:songIds)")
    suspend fun deleteSongsNotIn(songIds: List<Long>)
}