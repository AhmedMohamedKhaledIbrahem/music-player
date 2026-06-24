package com.androidinternals.musicplayer.feature.music.domain.entity

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Serializable
@Stable
data class Music(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String?,
    val duration: Long,
    val contentUri: String,
    val albumArtUri: String?
)

