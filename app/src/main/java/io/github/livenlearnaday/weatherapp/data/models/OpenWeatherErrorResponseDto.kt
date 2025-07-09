package io.github.livenlearnaday.weatherapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class OpenWeatherErrorResponseDto(
    val cod: String,
    val message: String
)
