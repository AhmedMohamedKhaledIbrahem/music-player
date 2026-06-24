package com.androidinternals.musicplayer.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.androidinternals.musicplayer.core.database.entity.FavoriteSongEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteSongDao : BaseDao<FavoriteSongEntity> {
    @Query("SELECT * FROM favorite_songs ORDER BY addedAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteSongEntity>>

    @Query("SELECT * FROM favorite_songs WHERE mediaStoreId = :musicId LIMIT 1")
    suspend fun getFavoriteById(musicId: Long): FavoriteSongEntity?

    @Query("DELETE FROM favorite_songs WHERE mediaStoreId = :musicId")
    suspend fun removeFavorite(musicId: Long)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_songs WHERE mediaStoreId = :musicId)")
    suspend fun isFavorite(musicId: Long): Boolean
}