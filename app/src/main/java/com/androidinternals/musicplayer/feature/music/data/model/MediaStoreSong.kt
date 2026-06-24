package com.androidinternals.musicplayer.feature.music.data.model

data class MediaStoreSong(
    val mediaStoreId: Long,
    val title: String,
    val artist: String,
    val album: String?,
    val duration: Long,
    val contentUri: String,
    val albumArtUri: String?,
    val dateAddedSeconds: Long
)
