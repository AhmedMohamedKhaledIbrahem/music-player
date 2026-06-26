package com.androidinternals.musicplayer.feature.music.data.service.local.player

import androidx.media3.common.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ProgressTracker {
    fun start(scope: CoroutineScope, player: Player, listener: PlayerListener)
    fun stop()
}

class ProgressTrackerImpl @Inject constructor(): ProgressTracker {
    private var job : Job? = null
    override fun start(
        scope: CoroutineScope,
        player: Player,
        listener: PlayerListener
    ) {
        stop()
        job = scope.launch {
            while (isActive){
                if (player.isPlaying){
                    listener.onProgressUpdate(
                        currentPosition = player.currentPosition,
                        duration = player.duration.coerceAtLeast(0L),
                        bufferedPosition = player.bufferedPosition,
                    )
                    listener.onQueueChanged(
                        hasNext = player.hasNextMediaItem(),
                        hasPrevious = player.hasPreviousMediaItem(),
                    )
                    delay(1000L)
                }
            }
        }
    }

    override fun stop() {
        job?.cancel()
        job = null
    }
}