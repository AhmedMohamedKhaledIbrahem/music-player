package com.androidinternals.musicplayer.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androidinternals.musicplayer.core.database.dao.FavoriteSongDao
import com.androidinternals.musicplayer.core.database.dao.MusicProviderDao
import com.androidinternals.musicplayer.core.database.dao.PlaylistDao
import com.androidinternals.musicplayer.core.database.dao.RecentlyPlayedDao
import com.androidinternals.musicplayer.core.database.entity.FavoriteSongEntity
import com.androidinternals.musicplayer.core.database.entity.PlaylistEntity
import com.androidinternals.musicplayer.core.database.entity.PlaylistSongEntity
import com.androidinternals.musicplayer.core.database.entity.RecentlyPlayedEntity

@Database(
    entities = [
        FavoriteSongEntity::class,
        PlaylistEntity::class,
        PlaylistSongEntity::class,
        RecentlyPlayedEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class MusicPlayerDatabase: RoomDatabase() {
    abstract fun favoriteSongDao(): FavoriteSongDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun musicProviderDao(): MusicProviderDao
    abstract fun recentlyPlayedDao(): RecentlyPlayedDao

}