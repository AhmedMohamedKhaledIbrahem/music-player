package com.androidinternals.musicplayer.feature.music.presentation.intent

import com.androidinternals.musicplayer.core.ui.ViewIntent
import com.androidinternals.musicplayer.feature.music.domain.entity.Music

sealed interface AudioIntent : ViewIntent {

    data class PlaySong(val song: Music) : AudioIntent
    data class PlayList(val songs: List<Music>, val startIndex: Int = 0) : AudioIntent
    data object Resume : AudioIntent
    data object Pause : AudioIntent
    data object Stop : AudioIntent
    data object Release: AudioIntent
    data class SeekTo(val positionMs: Long) : AudioIntent
    data object Next : AudioIntent
    data object Previous : AudioIntent
    data class SkipToIndex(val index: Int) : AudioIntent
    data class AddToQueue(val song: Music) : AudioIntent
    data class RemoveFromQueue(val song: Music) : AudioIntent
    data object ClearQueue : AudioIntent
}