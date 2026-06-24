package com.androidinternals.musicplayer.core.database.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recently_played")
data class RecentlyPlayedEntity(
    @PrimaryKey
    val mediaStoreId: Long,
    val playedAt: Long = System.currentTimeMillis(),
    val lastPositionMs: Long = 0L
)