package com.goldmansachs.assignment.model.repository

import androidx.lifecycle.LiveData
import com.goldmansachs.assignment.model.util.Resource
import com.goldmansachs.assignment.model.apiservice.ApodEntity
import com.goldmansachs.assignment.model.apiservice.ApodFavoriteEntity
import kotlinx.coroutines.flow.Flow

interface ApodRepository {
    fun getLatestApods(
        isRefresh : Boolean,
        param : Map<String, String>,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ): Flow<Resource<List<ApodEntity>>>

    fun getFavoriteApods(): List<ApodEntity>

    fun addToFavorite(entity: ApodFavoriteEntity)
    fun removeFromFavorite(entity: ApodFavoriteEntity)
    fun existsInFavorite(entity: ApodFavoriteEntity):Boolean
}