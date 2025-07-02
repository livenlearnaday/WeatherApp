package io.github.livenlearnaday.weatherapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorModel(
    val code: Int = 0,
    val type: String = "",
    val message: String = ""
)
