package com.androidinternals.musicplayer.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.androidinternals.musicplayer.core.database.entity.RecentlyPlayedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentlyPlayedDao {
    @Query(
        """
        SELECT * FROM recently_played
        ORDER BY playedAt DESC
        """
    )
    fun getAllRecentlyPlayed(): Flow<List<RecentlyPlayedEntity>>

    @Query(
        """
        SELECT * FROM recently_played
        WHERE mediaStoreId = :mediaStoreId
        LIMIT 1
        """
    )
    suspend fun getRecentlyPlayedById(
        mediaStoreId: Long
    ): RecentlyPlayedEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRecentlyPlayed(
        song: RecentlyPlayedEntity
    )

    @Query(
        """
        UPDATE recently_played
        SET lastPositionMs = :positionMs,
            playedAt = :playedAt
        WHERE mediaStoreId = :mediaStoreId
        """
    )
    suspend fun updatePlaybackPosition(
        mediaStoreId: Long,
        positionMs: Long,
        playedAt: Long = System.currentTimeMillis()
    )

    @Query("DELETE FROM recently_played WHERE mediaStoreId = :mediaStoreId")
    suspend fun removeRecentlyPlayed(mediaStoreId: Long)

    @Query("DELETE FROM recently_played")
    suspend fun clearRecentlyPlayed()
}