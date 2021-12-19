package com.goldmansachs.assignment.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.goldmansachs.assignment.api.ApodService
import com.goldmansachs.assignment.model.dao.ApodDao
import com.goldmansachs.assignment.model.util.Resource
import com.goldmansachs.assignment.model.util.networkBoundResource
import com.goldmansachs.assignment.model.apiservice.ApodEntity
import com.goldmansachs.assignment.model.apiservice.ApodFavoriteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApodRepositoryImpl @Inject constructor(
    private val appDao: ApodDao,
    private val apodService: ApodService
) :
    ApodRepository {

    override fun getLatestApods(
        isRefresh: Boolean,
        param: Map<String, String>,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ): Flow<Resource<List<ApodEntity>>> =
        networkBoundResource(
            loadFromDb = {
                appDao.getAll()
            },
            createCall = {
                apodService.getApodList(param)
            },
            saveToDb = { entities ->
                appDao.deleteAll()
                appDao.saveEntries(entities)

            },
            shouldFetch = {
                isRefresh
            },
            onCallSuccess = onSuccess,
            onCallFailed = onError
        )

    override fun getFavoriteApods(): List<ApodEntity>  = appDao.getAllFavorites()
    override fun addToFavorite(entity: ApodFavoriteEntity) {
        appDao.insertFav(entity)
    }

    override fun removeFromFavorite(entity: ApodFavoriteEntity) {
        appDao.deleteFav(entity)
    }

    override fun existsInFavorite(entity: ApodFavoriteEntity):Boolean {
        val status = appDao.existsInFav(entity.id)
        return status
    }

}