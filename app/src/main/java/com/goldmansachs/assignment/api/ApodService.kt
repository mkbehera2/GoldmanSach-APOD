package com.goldmansachs.assignment.api

import com.goldmansachs.assignment.model.apiservice.ApodEntity
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApodService {

    companion object {
        const val BASE_URL = "https://api.nasa.gov/"
    }

    @GET("planetary/apod")
    suspend fun getApodList(@QueryMap params : Map<String, String>): List<ApodEntity>
}