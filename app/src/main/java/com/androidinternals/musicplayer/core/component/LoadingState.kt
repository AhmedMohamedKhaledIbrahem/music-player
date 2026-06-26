package com.androidinternals.musicplayer.core.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingState(
    modifier: Modifier = Modifier,
    loading: Boolean,
    showContent: @Composable () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        } else {
            showContent()
        }
    }

}