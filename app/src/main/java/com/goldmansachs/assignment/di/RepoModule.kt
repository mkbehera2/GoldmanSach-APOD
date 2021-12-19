package com.goldmansachs.assignment.di

import com.goldmansachs.assignment.api.ApodService
import com.goldmansachs.assignment.model.dao.ApodDao
import com.goldmansachs.assignment.model.repository.ApodRepository
import com.goldmansachs.assignment.model.repository.ApodRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideApodRepository(appDao: ApodDao, apodService: ApodService): ApodRepository =
        ApodRepositoryImpl(appDao, apodService)
}