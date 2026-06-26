package com.androidinternals.musicplayer.feature.music.domain.entity

import androidx.compose.runtime.Stable
import com.androidinternals.musicplayer.core.ui.ViewState

@Stable
data class PlayBackState(
    val isPlaying: Boolean = false,
    val currentSong: String? = null,
    val currentArtist: String? = null,
    val albumArtUri: String? = null,
    val duration: Long = 0L,
    val currentPosition: Long = 0L,
    val bufferedPosition: Long = 0L,
    val playbackState: Int = PlaybackStateCode.IDLE,
    val hasNext: Boolean = false,
    val hasPrevious: Boolean = false,
) : ViewState {
    val progress: Float
        get() = if (duration > 0) currentPosition.toFloat() else 0f
}

object PlaybackStateCode {
    const val IDLE = 1
    const val BUFFERING = 2
    const val READY = 3
    const val ENDED = 4
}