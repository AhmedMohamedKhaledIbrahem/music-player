package com.androidinternals.musicplayer.feature.music.presentation.state

import androidx.compose.runtime.Stable
import com.androidinternals.musicplayer.core.ui.ViewState
import com.androidinternals.musicplayer.feature.music.domain.entity.Music

@Stable
data class MusicState(
    val isLoading: Boolean = false,
    val songs: List<Music> = emptyList(),
): ViewState
