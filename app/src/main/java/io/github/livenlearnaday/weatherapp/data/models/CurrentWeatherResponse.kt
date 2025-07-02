package io.github.livenlearnaday.weatherapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherResponse(
    @SerialName("request") var request: Request?,
    @SerialName("location") var location: Location?,
    @SerialName("current") var currentWeatherCondition: CurrentWeatherCondition?
)

@Serializable
data class Request(
    @SerialName("type") val type: String,
    @SerialName("query") val query: String,
    @SerialName("language") val language: String,
    @SerialName("unit") val unit: String
)

@Serializable
data class Location(
    @SerialName("name") val name: String,
    @SerialName("country") val country: String,
    @SerialName("region") val region: String,
    @SerialName("lat") val lat: Double,
    @SerialName("lon") val lng: Double,
    @SerialName("timezone_id") val timezoneId: String,
    @SerialName("localtime") val localtime: String,
    @SerialName("localtime_epoch") val localtimeEpoch: Int,
    @SerialName("utc_offset") val utcOffset: Double

)

@Serializable
data class CurrentWeatherCondition(
    @SerialName("observation_time") val observationTime: String,
    @SerialName("temperature") val temperature: Double,
    @SerialName("weather_code") val weatherCode: Int,
    @SerialName("weather_icons") val weatherIcons: List<String>,
    @SerialName("weather_descriptions") val weatherDescriptions: List<String>,
    @SerialName("wind_speed") val windSpeed: Int,
    @SerialName("wind_degree") val windDegree: Int,
    @SerialName("wind_dir") val windDir: String,
    @SerialName("pressure") val pressure: Int,
    @SerialName("precip") val precip: Double,
    @SerialName("humidity") val humidity: Int,
    @SerialName("cloudcover") val cloudCover: Int,
    @SerialName("feelslike") val feelsLike: Int,
    @SerialName("uv_index") val uvIndex: Int,
    @SerialName("visibility") val visibility: Int,
    @SerialName("is_day") val isDay: String

)
