package io.github.livenlearnaday.weatherapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorDto(
    @SerialName("code")
    val code: Int,
    @SerialName("type")
    val type: String,
    @SerialName("info")
    val message: String
)
