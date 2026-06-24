package com.androidinternals.musicplayer.feature.music.data.mapper

import com.androidinternals.musicplayer.core.database.entity.MusicEntity
import com.androidinternals.musicplayer.feature.music.domain.entity.Music

fun MusicEntity.toDomain() = Music(
    id = id,
    title = title,
    artist = artist,
    album = album,
    duration = duration,
    contentUri = contentUri,
    albumArtUri = albumArtUri
)
fun Music.toEntity(
    dateAddedSeconds: Long
) = MusicEntity(
    id = id,
    title = title,
    artist = artist,
    album = album,
    duration = duration,
    contentUri = contentUri,
    albumArtUri = albumArtUri,
    dateAddedSeconds = dateAddedSeconds
)
