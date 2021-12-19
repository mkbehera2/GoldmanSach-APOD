package com.goldmansachs.assignment.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.goldmansachs.assignment.model.dao.ApodDao
import com.goldmansachs.assignment.model.apiservice.ApodEntity
import com.goldmansachs.assignment.model.apiservice.ApodFavoriteEntity

@Database(entities = [ApodEntity::class, ApodFavoriteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun apodListDao(): ApodDao
}