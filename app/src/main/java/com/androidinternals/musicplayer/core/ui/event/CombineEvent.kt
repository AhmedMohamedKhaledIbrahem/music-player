package com.androidinternals.musicplayer.core.ui.event

import com.androidinternals.musicplayer.core.navigation.Route
import com.androidinternals.musicplayer.core.ui.UiText
import com.androidinternals.musicplayer.core.ui.ViewEvent


/**
 * Processes a list of [ViewEvent] and delegates each event to its corresponding handler.
 *
 * This function iterates through the given list of events and triggers the appropriate
 * action based on the event type:
 * - [ShowSnackBarEvent] → invokes [onShowMessage]
 * - [Navigation] → invokes [onNavigate]
 *
 * Designed to centralize UI event handling (e.g., in Compose or MVVM),
 * keeping the ViewModel free from direct UI logic.
 *
 * @param event List of [ViewEvent] to be processed.
 * @param onShowMessage Suspend callback triggered when a [ShowSnackBarEvent] occurs.
 * @param onNavigate Callback triggered when a [Navigation] navigation event occurs.
 */
suspend inline fun combineEvent(
    event: List<ViewEvent>,
    crossinline onShowMessage: suspend (message: UiText) -> Unit,
    crossinline onNavigate: (route: Route) -> Unit
) {
    event.forEach { viewEvent ->
        when (viewEvent) {
            is ShowSnackBarEvent -> onShowMessage(viewEvent.message)
            is Navigation -> onNavigate(viewEvent.route)
            else -> Unit
        }
    }
}
