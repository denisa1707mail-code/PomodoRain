package com.example.pomodorain.domain.model

data class WeatherSnapshot(
    val cityName: String,
    val latitude: Double,
    val longitude: Double,
    val temperature: Double,
    val weatherCode: Int,
    val windSpeed: Double,
    val fetchedAtMillis: Long,
    val isForecast: Boolean,
)