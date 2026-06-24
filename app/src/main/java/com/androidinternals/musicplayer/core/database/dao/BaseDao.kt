package com.androidinternals.musicplayer.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Upsert

@Dao
interface BaseDao<T> {
    @Upsert
    suspend fun upsert(entity: T)

    @Delete
    suspend fun delete(entity: T)
}