package com.example.pomodorain.di

import com.example.pomodorain.data.local.dao.StudySessionDao
import com.example.pomodorain.data.repository.SessionRepositoryImp
import com.example.pomodorain.domain.repository.SessionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSessionRepository(dao: StudySessionDao): SessionRepository =
        SessionRepositoryImp(dao)
}