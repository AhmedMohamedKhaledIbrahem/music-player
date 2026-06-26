package com.androidinternals.musicplayer.feature.music.data.service.local.player

import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.androidinternals.musicplayer.core.coroutine.ApplicationScopeMainThread
import com.androidinternals.musicplayer.feature.music.domain.entity.Music
import com.androidinternals.musicplayer.feature.music.domain.entity.PlayBackState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


interface PlayBackService {
    val playbackState: StateFlow<PlayBackState>
    fun play(song: Music)
    fun play(songs: List<Music>, startIndex: Int = 0)
    fun resume()
    fun pause()
    fun stop()
    fun seekTo(position: Long)
    fun next()
    fun previous()
    fun skipToIndex(index: Int)
    fun addToQueue(song: Music)
    fun removeFromQueue(song: Music)
    fun clearQueue()
    fun release()
}

class PlayBackServiceImpl @Inject constructor(
    private val player: ExoPlayer,
    private val listener: PlayerListener,
    private val progressTracker: ProgressTracker,
    @ApplicationScopeMainThread private val serviceScope: CoroutineScope
) : PlayBackService {

    override val playbackState: StateFlow<PlayBackState>
        get() = listener._state

    private val playbackStateChangedListener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            if (isPlaying) {
                progressTracker.start(serviceScope, player, listener)
            } else {
                progressTracker.stop()
            }
        }
    }

    init {
        player.addListener(listener)
        player.addListener(playbackStateChangedListener)
        player.prepare()
    }

    override fun play(song: Music) {
        play(listOf(song), startIndex = 0)
    }

    override fun play(
        songs: List<Music>,
        startIndex: Int
    ) {
        val mediaItem = songs.map { it.toMediaItem() }
        player.setMediaItems(mediaItem, startIndex, 0L)
        player.prepare()
        player.play()
        refreshQueueState()
    }

    override fun resume() {
        if (player.playbackState == Player.STATE_ENDED) {
            player.seekToDefaultPosition()
        }
        player.play()
    }

    override fun pause() {
        player.pause()
        progressTracker.stop()
    }

    override fun stop() {
        player.stop()
        progressTracker.stop()
    }

    override fun seekTo(position: Long) {
        player.seekTo(position)
    }

    override fun next() {
        if (player.hasNextMediaItem()) {
            player.seekToNextMediaItem()
            refreshQueueState()
        }
    }

    override fun previous() {
        if (player.currentPosition > 3_000L) {
            player.seekTo(0)
        } else if (player.hasPreviousMediaItem()) {
            player.seekToPreviousMediaItem()
            refreshQueueState()
        }
    }

    override fun skipToIndex(index: Int) {
        player.seekTo(index, 0L)
        refreshQueueState()
    }

    override fun addToQueue(song: Music) {
        player.addMediaItem(song.toMediaItem())
        refreshQueueState()
    }

    override fun removeFromQueue(song: Music) {
        for (index in 0 until player.mediaItemCount) {
            if (player.getMediaItemAt(index).mediaId == song.id.toString()) {
                player.removeMediaItem(index)
                break
            }
        }
        refreshQueueState()
    }

    override fun clearQueue() {
        player.clearMediaItems()
        player.stop()
        refreshQueueState()
    }

    override fun release() {
        progressTracker.stop()
        player.removeListener(listener)
        player.removeListener(playbackStateChangedListener)
        player.release()
        serviceScope.cancel()
    }

    private fun refreshQueueState() {
        listener.onQueueChanged(
            hasNext = player.hasNextMediaItem(),
            hasPrevious = player.hasPreviousMediaItem(),
        )
    }

    private fun Music.toMediaItem(): MediaItem =
        MediaItem.Builder()
            .setMediaId(id.toString())
            .setUri(contentUri)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(title)
                    .setArtist(artist)
                    .setArtworkUri(albumArtUri?.toUri())
                    .build()
            )
            .build()
}