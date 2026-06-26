package com.androidinternals.musicplayer.feature.music.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.androidinternals.musicplayer.core.navigation.Route
import com.androidinternals.musicplayer.core.ui.UiText
import com.androidinternals.musicplayer.core.ui.base.MviViewModel
import com.androidinternals.musicplayer.core.ui.event.UiEvent
import com.androidinternals.musicplayer.feature.music.domain.entity.Music
import com.androidinternals.musicplayer.feature.music.domain.usecase.AddSongsFromMusicProviderUseCase
import com.androidinternals.musicplayer.feature.music.domain.usecase.GetAllSongsUseCase
import com.androidinternals.musicplayer.feature.music.presentation.intent.MusicIntent
import com.androidinternals.musicplayer.feature.music.presentation.state.MusicState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val getAllSongsUseCase: GetAllSongsUseCase,
    private val addSongsFromMusicProviderUseCase: AddSongsFromMusicProviderUseCase
) : MviViewModel<MusicState, MusicIntent, UiEvent>() {
    override val initialState: MusicState
        get() = MusicState()
    override fun onIntent(intent: MusicIntent) {
        when (intent) {
            MusicIntent.AddSongsFromMusicProvider -> addSongsFromMusicProvider()
            MusicIntent.LoadSongs -> loadSong()
            MusicIntent.RequestPermissions -> sendEvent(UiEvent.TriggerPermissionCheck)
            MusicIntent.ShowMessagePermissionDenied -> sendEvent(UiEvent.ShowSnackBar(UiText.from("Permission denied")))
            is MusicIntent.SongDetailsClickItem -> songDetails(intent.song)

        }
    }

    private fun addSongsFromMusicProvider() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            try {
                addSongsFromMusicProviderUseCase.invoke()
                sendEvent(UiEvent.Navigate(Route.MusicScreen))
            } catch (e: Exception) {
                sendEvent(UiEvent.ShowSnackBar(UiText.from(e.message ?: "Something went wrong")))
            } finally {
                updateState { it.copy(isLoading = false) }
            }
        }
    }

    private fun loadSong() {
        getAllSongsUseCase.invoke()
            .onStart {
                updateState { it.copy(isLoading = true) }
            }.onEach { songs ->
                updateState {
                    it.copy(songs = songs, isLoading = false)
                }
            }.catch { e ->
                updateState {
                    it.copy(isLoading = false)
                }
                sendEvent(UiEvent.ShowSnackBar(UiText.from(e.message ?: "Something went wrong")))
            }.launchIn(viewModelScope)
    }
    private fun songDetails(song: Music){
        try {
            updateState { it.copy(isLoading = true) }
            sendEvent(UiEvent.Navigate(Route.MusicDetailsScreen(song)))
        }catch (e: Exception){
            updateState { it.copy(isLoading = false) }
            sendEvent(UiEvent.ShowSnackBar(UiText.from(e.message ?: "Something went wrong")))
        }
    }
}