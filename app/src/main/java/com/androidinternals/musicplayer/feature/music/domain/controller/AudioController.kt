package com.androidinternals.musicplayer.feature.music.domain.controller

import com.androidinternals.musicplayer.feature.music.domain.entity.Music
import com.androidinternals.musicplayer.feature.music.domain.entity.PlayBackState
import kotlinx.coroutines.flow.StateFlow

interface AudioController {
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