package com.example.pomodorain.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_sessions")
data class StudySessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val startedAtMillis: Long,
    val durationSeconds: Int,
    val isCompleted: Boolean,

    val startCityName: String?,
    val startLatitude: Double?,
    val startLongitude: Double?,
    val startTemperature: Double?,
    val startWeatherCode: Int?,
    val startWindSpeed: Double?,
    val startWeatherFetchedAtMillis: Long?,

    val endCityName: String?,
    val endLatitude: Double?,
    val endLongitude: Double?,
    val endTemperature: Double?,
    val endWeatherCode: Int?,
    val endWindSpeed: Double?,
    val endWeatherFetchedAtMillis: Long?,

    val endWeatherIsForecast: Boolean?,
    )