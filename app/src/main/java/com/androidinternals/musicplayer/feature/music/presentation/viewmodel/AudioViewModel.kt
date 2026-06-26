package com.androidinternals.musicplayer.feature.music.presentation.viewmodel

import com.androidinternals.musicplayer.core.navigation.Route
import com.androidinternals.musicplayer.core.ui.base.MviViewModel
import com.androidinternals.musicplayer.core.ui.event.UiEvent
import com.androidinternals.musicplayer.feature.music.domain.controller.AudioController
import com.androidinternals.musicplayer.feature.music.domain.entity.PlayBackState
import com.androidinternals.musicplayer.feature.music.presentation.intent.AudioIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AudioViewModel @Inject constructor(
    private val audioController: AudioController,
): MviViewModel<PlayBackState, AudioIntent, UiEvent>() {
    override val initialState: PlayBackState
        get() = audioController.playbackState.value

    override fun onIntent(intent: AudioIntent) {
        when (intent) {
            AudioIntent.Resume -> audioController.resume()
            AudioIntent.Pause -> audioController.pause()
            AudioIntent.Stop -> audioController.stop()
            AudioIntent.Next -> audioController.next()
            AudioIntent.Previous -> audioController.previous()
            AudioIntent.ClearQueue -> audioController.clearQueue()
            AudioIntent.Release -> audioController.release()
            is AudioIntent.SkipToIndex -> audioController.skipToIndex(intent.index)
            is AudioIntent.AddToQueue -> audioController.addToQueue(intent.song)
            is AudioIntent.RemoveFromQueue -> audioController.removeFromQueue(intent.song)
            is AudioIntent.SeekTo -> audioController.seekTo(intent.positionMs)
            is AudioIntent.PlaySong -> audioController.play(intent.song)
            is AudioIntent.PlayList -> {
                audioController.play(intent.songs, intent.startIndex)
                sendEvent(UiEvent.Navigate(Route.MusicDetailsScreen))
            }

        }
    }
    override fun onCleared() {
        super.onCleared()
        audioController.release()
    }
}