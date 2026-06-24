package com.androidinternals.musicplayer.core.navigation

import androidx.navigation3.runtime.NavKey
import com.androidinternals.musicplayer.core.ui.ViewEvent
import com.androidinternals.musicplayer.feature.music.domain.entity.Music
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : ViewEvent, NavKey {
    @Serializable
    data object HomeScreen : Route, ViewEvent, NavKey
    @Serializable
    data class MusicScreen(val music: Music)
    @Serializable
    data object SettingsScreen


}