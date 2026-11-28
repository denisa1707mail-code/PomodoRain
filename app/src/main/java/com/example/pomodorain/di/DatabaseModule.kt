package com.example.pomodorain.di

import android.content.Context
import androidx.room.Room
import com.example.pomodorain.data.local.dao.StudySessionDao
import com.example.pomodorain.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "pomodorain_database"
        ).build()
    }

    @Provides
    fun provideStudySessionDao(database: AppDatabase): StudySessionDao =
        database.studySessionDao()
}