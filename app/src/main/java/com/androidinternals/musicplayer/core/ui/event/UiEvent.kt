package com.androidinternals.musicplayer.core.ui.event

import com.androidinternals.musicplayer.core.navigation.Route
import com.androidinternals.musicplayer.core.ui.UiText
import com.androidinternals.musicplayer.core.ui.ViewEvent

typealias ShowSnackBarEvent = UiEvent.ShowSnackBar
typealias Navigation = UiEvent.Navigate

sealed interface UiEvent : ViewEvent {
    data class ShowSnackBar(val message: UiText) : UiEvent
    data class Navigate(val route: Route) : UiEvent
    data class CombineEvents(val events: List<ViewEvent>) : UiEvent
    data object TriggerPermissionCheck: UiEvent
}