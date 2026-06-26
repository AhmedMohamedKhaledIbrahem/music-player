package com.androidinternals.musicplayer

import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.androidinternals.musicplayer.core.navigation.Route
import com.androidinternals.musicplayer.core.ui.event.UiEvent
import com.androidinternals.musicplayer.feature.music.presentation.intent.MusicIntent
import com.androidinternals.musicplayer.feature.music.presentation.viewmodel.MusicViewModel

@Composable
fun SplashScreen(
    musicViewModel: MusicViewModel,
    snackbarHostState: SnackbarHostState,
    onNavigateToMusicScreen: () -> Unit
) {

    LaunchedEffect(Unit) {
        musicViewModel.onIntent(MusicIntent.RequestPermissions)
    }

    val context = LocalContext.current
    val targetPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        android.Manifest.permission.READ_MEDIA_AUDIO
    } else {
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            musicViewModel.onIntent(MusicIntent.AddSongsFromMusicProvider)
        } else {
            musicViewModel.onIntent(MusicIntent.ShowMessagePermissionDenied)
        }
    }


    LaunchedEffect(musicViewModel.event) {
        musicViewModel.event.collect { event ->
            when(event){
                is UiEvent.Navigate -> {
                    when(event.route){
                        Route.MusicScreen -> onNavigateToMusicScreen()
                        else -> Unit
                    }
                }
                is UiEvent.TriggerPermissionCheck -> {
                    val hasPermission = ContextCompat.checkSelfPermission(
                        context, targetPermission
                    ) == PackageManager.PERMISSION_GRANTED

                    if (hasPermission) {
                        musicViewModel.onIntent(MusicIntent.AddSongsFromMusicProvider)

                    } else {
                        permissionLauncher.launch(targetPermission)
                    }
                }
                is UiEvent.ShowSnackBar -> {

                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
                else -> Unit

            }
        }
    }


}