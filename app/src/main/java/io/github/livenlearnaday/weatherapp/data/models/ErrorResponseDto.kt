package io.github.livenlearnaday.weatherapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseDto(
    val success: Boolean,
    val error: ErrorDto
)
