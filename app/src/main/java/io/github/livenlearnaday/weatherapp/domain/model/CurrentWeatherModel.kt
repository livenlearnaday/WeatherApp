package io.github.livenlearnaday.weatherapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherModel(
    var request: RequestModel = RequestModel(),
    var location: LocationModel = LocationModel(),
    var currentWeatherCondition: CurrentWeatherConditionModel = CurrentWeatherConditionModel()
)

@Serializable
data class RequestModel(
    val type: String = "",
    val query: String = "",
    val language: String = "",
    val unit: String = ""
)

@Serializable
data class LocationModel(
    val name: String = "",
    val country: String = "",
    val region: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val timeZoneId: String = "",
    val localTime: String = "",
    val localTimeEpoch: Int = 0,
    val utcOffset: Double = 0.0

)

@Serializable
data class CurrentWeatherConditionModel(
    val observationTime: String = "",
    val temperature: Double = 0.0,
    val weatherCode: Int = 0,
    val weatherIcons: List<String> = emptyList<String>(),
    val weatherDescriptions: List<String> = emptyList<String>(),
    val windSpeed: Int = 0,
    val windDegree: Int = 0,
    val windDir: String = "",
    val pressure: Int = 0,
    val precip: Double = 0.0,
    val humidity: Int = 0,
    val cloudCover: Int = 0,
    val feelsLike: Int = 0,
    val uvIndex: Int = 0,
    val visibility: Int = 0,
    val isDay: String = ""

)
