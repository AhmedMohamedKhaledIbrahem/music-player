package com.androidinternals.musicplayer.feature.music.presentation.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.androidinternals.musicplayer.R
import com.androidinternals.musicplayer.feature.music.presentation.intent.AudioIntent
import com.androidinternals.musicplayer.feature.music.presentation.viewmodel.AudioViewModel
import java.util.concurrent.TimeUnit


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicDetailsScreen(
    modifier: Modifier = Modifier,
    audioViewModel: AudioViewModel,
    onNavigateBack: () -> Unit,
) {
    val state by audioViewModel.state.collectAsStateWithLifecycle()

    NowPlayingContent(
        onPlayPause = {
            if (state.isPlaying) {
                audioViewModel.onIntent(
                    AudioIntent.Pause
                )

            } else {
                audioViewModel.onIntent(
                    AudioIntent.Resume
                )
            }
        },
        onPrevious = { audioViewModel.onIntent(AudioIntent.Previous) },
        onNext = { audioViewModel.onIntent(AudioIntent.Next) },
        onSeek = { audioViewModel.onIntent(AudioIntent.SeekTo(it))},
        albumArtUri = state.albumArtUri,
        currentSong = state.currentSong,
        currentArtist = state.currentArtist,
        stateProgress = state.progress,
        currentPosition = state.currentPosition,
        duration = state.duration,
        hasPrevious = state.hasPrevious,
        isPlaying = state.isPlaying,
        hasNext = state.hasNext,
        modifier = modifier,
    )

}

@Composable
private fun NowPlayingContent(
    albumArtUri: String?,
    currentSong: String?,
    currentArtist: String?,
    stateProgress: Float,
    currentPosition: Long,
    duration: Long,
    hasPrevious: Boolean,
    isPlaying: Boolean,
    hasNext: Boolean,
    onPlayPause: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onSeek: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // ── Album art ──────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
        ) {
            AsyncImage(
                model = albumArtUri,
                contentDescription = "Album art",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.baseline_library_music_24),
                error = painterResource(R.drawable.baseline_library_music_24),
                fallback = painterResource(R.drawable.baseline_library_music_24),
            )
        }

        Spacer(Modifier.height(32.dp))

        // ── Song info ──────────────────────────────────────────────────────
        Text(
            text = currentSong ?: "Unknown",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .basicMarquee(),
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = currentArtist ?: "Unknown artist",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .basicMarquee(),
        )

        Spacer(Modifier.height(32.dp))

        // ── Seek bar ───────────────────────────────────────────────────────
        val onSeekUpdate by rememberUpdatedState { fraction: Float ->
            onSeek((fraction * duration).toLong())

        }
        Slider(
            value = stateProgress,
            onValueChange = { fraction ->
                onSeekUpdate(fraction)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
            ),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = currentPosition.toTimestamp(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            )
            Text(
                text = duration.toTimestamp(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            )
        }

        Spacer(Modifier.height(24.dp))

        // ── Transport controls ─────────────────────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = onPrevious,
                enabled = hasPrevious,
                modifier = Modifier.size(56.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.SkipPrevious,
                    contentDescription = "Previous",
                    modifier = Modifier.fillMaxSize(),
                    tint = if (hasPrevious)
                        MaterialTheme.colorScheme.onSurface
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                )
            }

            IconButton(
                onClick = onPlayPause,
                modifier = Modifier
                    .size(72.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(50),
                    ),
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    modifier = Modifier.size(36.dp),
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }

            IconButton(
                onClick = onNext,
                enabled = hasNext,
                modifier = Modifier.size(56.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.SkipNext,
                    contentDescription = "Next",
                    modifier = Modifier.fillMaxSize(),
                    tint = if (hasNext)
                        MaterialTheme.colorScheme.onSurface
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                )
            }
        }
    }
}

// ── Helpers ───────────────────────────────────────────────────────────────────

private fun Long.toTimestamp(): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this) % 60
    return "%d:%02d".format(minutes, seconds)
}