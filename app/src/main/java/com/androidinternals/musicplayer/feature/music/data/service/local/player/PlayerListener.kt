package com.androidinternals.musicplayer.feature.music.data.service.local.player

import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import com.androidinternals.musicplayer.feature.music.domain.entity.PlayBackState
import com.androidinternals.musicplayer.feature.music.domain.entity.PlaybackStateCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class PlayerListener @Inject constructor(): Player.Listener {
    internal val _state = MutableStateFlow(PlayBackState())

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        _state.update { it.copy(isPlaying = isPlaying) }
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        val stateCode = when(playbackState){
            Player.STATE_IDLE -> PlaybackStateCode.IDLE
            Player.STATE_BUFFERING -> PlaybackStateCode.BUFFERING
            Player.STATE_READY -> PlaybackStateCode.READY
            Player.STATE_ENDED -> PlaybackStateCode.ENDED
            else -> PlaybackStateCode.IDLE
        }
        _state.update { it.copy(playbackState = stateCode) }
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        val metadata = mediaItem?.mediaMetadata
        _state.update {
            it.copy(
                currentSong = metadata?.title?.toString(),
                currentArtist = metadata?.artist?.toString(),
                albumArtUri = metadata?.artworkUri?.toString(),
                currentPosition = 0L,
            )
        }
    }

    override fun onPlayerError(error: PlaybackException) {
        _state.update { it.copy(playbackState = PlaybackStateCode.IDLE, isPlaying = false) }
    }
    fun onQueueChanged(hasNext: Boolean, hasPrevious: Boolean) {
        _state.update { it.copy(hasNext = hasNext, hasPrevious = hasPrevious) }
    }
    fun onProgressUpdate(currentPosition: Long, duration: Long, bufferedPosition: Long) {
        _state.update {
            it.copy(
                currentPosition = currentPosition,
                duration = duration,
                bufferedPosition = bufferedPosition,
            )
        }
    }
}