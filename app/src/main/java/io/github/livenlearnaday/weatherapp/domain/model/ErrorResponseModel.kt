package io.github.livenlearnaday.weatherapp.domain.model

data class ErrorResponseModel(
    val success: Boolean = false,
    val error: ErrorModel = ErrorModel()
)
