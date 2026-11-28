package com.example.pomodorain.data.repository.mappers

import com.example.pomodorain.data.local.entity.StudySessionEntity
import com.example.pomodorain.domain.model.StudySession
import com.example.pomodorain.domain.model.WeatherSnapshot

fun StudySessionEntity.toDomain(): StudySession {
    return StudySession(
        id = id,
        startedAtMillis = startedAtMillis,
        durationSeconds = durationSeconds,
        isCompleted = isCompleted,
        startWeather = if (startCityName != null) {
            WeatherSnapshot(
                cityName = startCityName,
                latitude = startLatitude ?: 0.0,
                longitude = startLongitude ?: 0.0,
                temperature = startTemperature ?: 0.0,
                weatherCode = startWeatherCode ?: 0,
                windSpeed = startWindSpeed ?: 0.0,
                fetchedAtMillis = startWeatherFetchedAtMillis ?: 0L,
                isForecast = false
            )
        } else null,
        endWeather = if (endCityName != null) {
            WeatherSnapshot(
                cityName = endCityName,
                latitude = endLatitude ?: 0.0,
                longitude = endLongitude ?: 0.0,
                temperature = endTemperature ?: 0.0,
                weatherCode = endWeatherCode ?: 0,
                windSpeed = endWindSpeed ?: 0.0,
                fetchedAtMillis = endWeatherFetchedAtMillis ?: 0L,
                isForecast = endWeatherIsForecast ?: false
            )
        } else null
    )
}

fun StudySession.toEntity(): StudySessionEntity {
    return StudySessionEntity(
        id = id,
        startedAtMillis = startedAtMillis,
        durationSeconds = durationSeconds,
        isCompleted = isCompleted,
        startCityName = startWeather?.cityName,
        startLatitude = startWeather?.latitude,
        startLongitude = startWeather?.longitude,
        startTemperature = startWeather?.temperature,
        startWeatherCode = startWeather?.weatherCode,
        startWindSpeed = startWeather?.windSpeed,
        startWeatherFetchedAtMillis = startWeather?.fetchedAtMillis,
        endCityName = endWeather?.cityName,
        endLatitude = endWeather?.latitude,
        endLongitude = endWeather?.longitude,
        endTemperature = endWeather?.temperature,
        endWeatherCode = endWeather?.weatherCode,
        endWindSpeed = endWeather?.windSpeed,
        endWeatherFetchedAtMillis = endWeather?.fetchedAtMillis,
        endWeatherIsForecast = endWeather?.isForecast
    )
}