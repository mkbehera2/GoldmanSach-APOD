package com.goldmansachs.assignment.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.goldmansachs.assignment.model.apiservice.ApodEntity
import com.goldmansachs.assignment.model.apiservice.ApodFavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ApodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEntries(list: List<ApodEntity>)

    @Query("SELECT * FROM apod ORDER BY apod.date DESC")
    fun getAll(): Flow<List<ApodEntity>>

    @Query("DELETE FROM apod")
    suspend fun deleteAll()

    @Query("SELECT * FROM favorite ORDER BY favorite.date DESC")
    fun getAllFavorites(): List<ApodEntity>

    @Delete
    fun deleteFav(entity: ApodFavoriteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFav(entity: ApodFavoriteEntity)

    @Query("SELECT EXISTS (SELECT 1 FROM favorite WHERE id = :id)")
    fun existsInFav(id: Int): Boolean

}