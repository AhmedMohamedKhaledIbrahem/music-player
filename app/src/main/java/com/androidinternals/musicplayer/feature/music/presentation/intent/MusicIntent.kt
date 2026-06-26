package com.androidinternals.musicplayer.feature.music.presentation.intent

import com.androidinternals.musicplayer.core.ui.ViewIntent
import com.androidinternals.musicplayer.feature.music.domain.entity.Music

sealed interface MusicIntent: ViewIntent {
    data object LoadSongs: MusicIntent
    data object AddSongsFromMusicProvider: MusicIntent
    data object RequestPermissions: MusicIntent
    data object ShowMessagePermissionDenied: MusicIntent
}