package com.androidinternals.musicplayer.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class MusicEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val artist: String,
    val album: String?,
    val duration: Long,
    val contentUri: String,
    val albumArtUri: String?,
    val dateAddedSeconds: Long
)