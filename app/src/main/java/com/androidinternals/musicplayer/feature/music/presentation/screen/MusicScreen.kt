package com.androidinternals.musicplayer.feature.music.presentation.screen

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.androidinternals.musicplayer.R
import com.androidinternals.musicplayer.core.component.LoadingState
import com.androidinternals.musicplayer.feature.music.domain.entity.Music
import com.androidinternals.musicplayer.feature.music.presentation.intent.MusicIntent
import com.androidinternals.musicplayer.feature.music.presentation.viewmodel.MusicViewModel

@Composable
fun MusicScreenRoot(
    musicViewModel: MusicViewModel,
    modifier: Modifier = Modifier,
    onNavigateToMusicDetails: (music: Music) -> Unit,
) {
    val musicList by musicViewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        musicViewModel.onIntent(MusicIntent.LoadSongs)
    }
    val onNavigateToMusicDetailsRemember by rememberUpdatedState(onNavigateToMusicDetails)

    LoadingState(
        loading = musicList.isLoading,
        showContent = {
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth(),
            ) {
                if (musicList.songs.isEmpty()) {
                    item {
                        Box(
                            modifier = modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("songs not found")
                        }
                    }
                } else {
                    items(
                        items = musicList.songs,
                        key = { it.id }
                    ) { music ->
                        MusicCard(
                            //modifier = Modifier,
                            onItemClick = {
                                onNavigateToMusicDetailsRemember(music)
                            },
                            onIconClick = {},
                            contentUri = music.contentUri,
                            title = music.title,
                            artist = music.artist
                        )
                    }
                }
            }
        }
    )

}

@Composable
fun MusicCard(
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit,
    onIconClick: () -> Unit,
    contentUri: String,
    title: String,
    artist: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.surface
        ),
        onClick = onItemClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)


    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = contentUri,
                contentDescription = "image music",
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.baseline_library_music_24),
                error = painterResource(R.drawable.baseline_library_music_24),
                fallback = painterResource(R.drawable.baseline_library_music_24)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    modifier = Modifier.basicMarquee(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)

                )
                Text(
                    text = artist,
                    modifier = Modifier.basicMarquee(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            IconButton(
                onClick = onIconClick
            ) {
                Icon(
                    imageVector = Icons.Default.MoreHoriz,
                    contentDescription = "More",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}