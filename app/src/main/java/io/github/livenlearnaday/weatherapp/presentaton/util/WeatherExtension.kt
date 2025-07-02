package io.github.livenlearnaday.weatherapp.presentaton.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import io.github.livenlearnaday.weatherapp.ui.theme.lightOrange
import io.github.livenlearnaday.weatherapp.ui.theme.orange
import io.github.livenlearnaday.weatherapp.ui.theme.red

fun getFormattedUVRange(uv: Double): String = when {
    uv <= 2 -> "Low"
    uv > 2 && uv <= 5 -> "Moderate"
    uv > 5 && uv <= 7 -> "High"
    uv > 7 && uv <= 11 -> "Very High"
    else -> "Extreme"
}

fun getUVIndexColor(level: String): Color = when (level) {
    "Low" -> Color.Green
    "Moderate" -> Yellow
    "High" -> lightOrange
    "Very High" -> orange
    "Extreme" -> red
    else -> Color.White
}

fun getHumidityInPercent(humidity: Int): String = "$humidity%"
