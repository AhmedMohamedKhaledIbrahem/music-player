package com.androidinternals.musicplayer.core.navigation

import androidx.navigation3.runtime.NavKey
import com.androidinternals.musicplayer.core.ui.ViewEvent
import com.androidinternals.musicplayer.feature.music.domain.entity.Music
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : ViewEvent, NavKey {
    @Serializable
    data object SplashScreen : Route, ViewEvent, NavKey
    @Serializable
    data object MusicScreen:Route, ViewEvent, NavKey
    @Serializable
    data object MusicDetailsScreen:Route, ViewEvent, NavKey
    @Serializable
    data object SettingsScreen:Route, ViewEvent, NavKey


}