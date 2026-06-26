package com.androidinternals.musicplayer.feature.music.data.service.local.player

import com.androidinternals.musicplayer.feature.music.domain.controller.AudioController
import com.androidinternals.musicplayer.feature.music.domain.entity.Music
import com.androidinternals.musicplayer.feature.music.domain.entity.PlayBackState
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AudioPlayerControllerImpl @Inject constructor(
    private val playBackService: PlayBackService,
) : AudioController {
    override val playbackState: StateFlow<PlayBackState> = playBackService.playbackState
    override fun play(song: Music) = playBackService.play(song)
    override fun play(
        songs: List<Music>,
        startIndex: Int
    ) = playBackService.play(songs, startIndex)

    override fun resume() = playBackService.resume()

    override fun pause() = playBackService.pause()

    override fun stop() = playBackService.stop()

    override fun seekTo(position: Long) = playBackService.seekTo(position)

    override fun next() = playBackService.next()

    override fun previous() = playBackService.previous()

    override fun skipToIndex(index: Int) = playBackService.skipToIndex(index)

    override fun addToQueue(song: Music) = playBackService.addToQueue(song)

    override fun removeFromQueue(song: Music) = playBackService.removeFromQueue(song)

    override fun clearQueue() = playBackService.clearQueue()

    override fun release() = playBackService.release()
}