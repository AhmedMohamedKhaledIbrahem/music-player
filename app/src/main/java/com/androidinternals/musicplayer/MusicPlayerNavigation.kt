package com.androidinternals.musicplayer

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.androidinternals.musicplayer.core.navigation.Route
import com.androidinternals.musicplayer.feature.music.presentation.screen.MusicDetailsScreen
import com.androidinternals.musicplayer.feature.music.presentation.screen.MusicScreenRoot
import com.androidinternals.musicplayer.feature.music.presentation.viewmodel.AudioViewModel
import com.androidinternals.musicplayer.feature.music.presentation.viewmodel.MusicViewModel

@Composable
fun MusicPlayerNavigation(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState
) {
    val backStack = rememberNavBackStack(Route.SplashScreen)
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {

            entry<Route.SplashScreen> {
                val musicViewModel = hiltViewModel<MusicViewModel>()
                SplashScreen(
                    musicViewModel = musicViewModel,
                    snackbarHostState = snackbarHostState,
                    onNavigateToMusicScreen = {
                        backStack.remove(Route.SplashScreen)
                        backStack.add(Route.MusicScreen)
                    }
                )
            }
            entry<Route.MusicScreen> {
                val musicViewModel = hiltViewModel<MusicViewModel>()
                val audioViewModel = hiltViewModel<AudioViewModel>()
                MusicScreenRoot(
                    musicViewModel = musicViewModel,
                    audioViewModel = audioViewModel,
                    onNavigateToMusicDetails = {
                        backStack.add(Route.MusicDetailsScreen)
                    }
                )

            }
            entry<Route.MusicDetailsScreen> {
                val audioViewModel = hiltViewModel<AudioViewModel>()

                MusicDetailsScreen(
                    modifier = modifier,
                    audioViewModel =audioViewModel,
                    onNavigateBack = {
                        backStack.remove(Route.MusicDetailsScreen)
                        backStack.add(Route.MusicScreen)
                    }
                )
            }
            entry<Route.SettingsScreen> {

            }
        }


    )
}
