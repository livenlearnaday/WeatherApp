package io.github.livenlearnaday.weatherapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenWeatherCurrentWeatherResponse(
    @SerialName("coord") val coordinates: Coordinates,
    @SerialName("weather") var openWeatherResponseList: List<OpenWeatherWeatherResponse>,
    @SerialName("main") val mainResponse: MainResponse,
    @SerialName("visibility") val visibility: Int,
    @SerialName("wind") val windResponse: WindResponse,
    @SerialName("dt") val dt: Int,
    @SerialName("sys") val sysResponse: SysResponse,
    @SerialName("timezone") val timezone: Int,
    @SerialName("name") val name: String
)

@Serializable
data class Coordinates(
    @SerialName("lat") val lat: Double,
    @SerialName("lon") val lng: Double
)

@Serializable
data class WindResponse(
    @SerialName("speed") val windSpeed: Double,
    @SerialName("deg") val windDegree: Int,
    @SerialName("gust") val windGust: Double
)

@Serializable
data class SysResponse(
    @SerialName("country") val country: String,
    @SerialName("sunrise") val sunrise: Int,
    @SerialName("sunset") val sunset: Int
)

@Serializable
data class MainResponse(
    @SerialName("temp") val temp: Double,
    @SerialName("feels_like") val feelsLike: Double,
    @SerialName("pressure") val pressure: Int,
    @SerialName("humidity") val humidity: Int
)

@Serializable
data class OpenWeatherWeatherResponse(
    @SerialName("id") val id: Int,
    @SerialName("main") val main: String,
    @SerialName("description") val description: String,
    @SerialName("icon") val icon: String
)
