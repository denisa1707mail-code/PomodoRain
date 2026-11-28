package com.example.pomodorain.domain.model

data class StudySession(
    val id: Long = 0,
    val startedAtMillis: Long,
    val durationSeconds: Int,
    val isCompleted: Boolean,
    val startWeather: WeatherSnapshot?,
    val endWeather: WeatherSnapshot?,
)