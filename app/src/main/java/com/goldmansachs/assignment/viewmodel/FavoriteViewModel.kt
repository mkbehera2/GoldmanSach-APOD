package com.goldmansachs.assignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldmansachs.assignment.di.key.ApiKey
import com.goldmansachs.assignment.model.repository.ApodRepository
import com.goldmansachs.assignment.model.util.*
import com.goldmansachs.assignment.model.apiservice.ApodPost
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.LiveData
import com.goldmansachs.assignment.model.apiservice.ApodEntity
import com.goldmansachs.assignment.model.apiservice.ApodFavoriteEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: ApodRepository) : ViewModel() {

    val favList = MutableLiveData<List<ApodEntity>>()
    fun allFavoritesItems() {
        val latestList = repository.getFavoriteApods()
        if (latestList != null){
            favList.postValue(latestList)
        }
    }

    fun addToFavorite(entity: ApodFavoriteEntity) = repository.addToFavorite(entity)
    fun removeFromFavorite(entity: ApodFavoriteEntity) = repository.removeFromFavorite(entity)
    fun existsInFavorite(entity: ApodFavoriteEntity) = repository.existsInFavorite(entity)

}