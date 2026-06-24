package com.androidinternals.musicplayer.core.database.entity

import androidx.room.Entity


@Entity(
    tableName = "playlist_songs",
    primaryKeys = ["playlistId", "mediaStoreId"]
)
data class PlaylistSongEntity(
    val playlistId: Long,
    val mediaStoreId: Long,
    val addedAt: Long = System.currentTimeMillis()
)