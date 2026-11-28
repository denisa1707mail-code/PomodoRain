package com.example.pomodorain.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pomodorain.data.local.dao.StudySessionDao
import com.example.pomodorain.data.local.entity.StudySessionEntity

@Database(
    entities = [StudySessionEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studySessionDao(): StudySessionDao
}