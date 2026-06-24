package com.androidinternals.musicplayer.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_songs")
data class FavoriteSongEntity(
    @PrimaryKey
    val mediaStoreId: Long,
    val title: String,
    val artist: String,
    val album: String?,
    val duration: Long,
    val contentUri: String,
    val albumArtUri: String?,
    val addedAt: Long = System.currentTimeMillis()
)
