package io.github.livenlearnaday.weatherapp.presentaton.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import io.github.livenlearnaday.weatherapp.ui.theme.lightOrange
import io.github.livenlearnaday.weatherapp.ui.theme.orange
import io.github.livenlearnaday.weatherapp.ui.theme.red
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime

fun getFormattedUVRange(uv: Double): String = when {
    uv == -99.0 -> "NA"
    uv <= 2 && uv != -99.0 -> "Low"
    uv > 2 && uv <= 5 -> "Moderate"
    uv > 5 && uv <= 7 -> "High"
    uv > 7 && uv <= 11 -> "Very High"
    else -> "Extreme"
}

fun getUVIndexColor(level: String): Color = when (level) {
    "NA" -> Color.Transparent
    "Low" -> Color.Green
    "Moderate" -> Yellow
    "High" -> lightOrange
    "Very High" -> orange
    "Extreme" -> red
    else -> Color.White
}

fun getHumidityInPercent(humidity: Int): String = "$humidity%"

fun getOpenWeatherIconUrl(icon: String): String = "https://openweathermap.org/img/wn/$icon@2x.png"

fun getWindDirection(winDegreeInt: Int): String = when (winDegreeInt) {
    45 -> "NE"
    90 -> "E"
    135 -> "SE"
    180 -> "S"
    225 -> "SW"
    270 -> "W"
    315 -> "NW"
    else -> "N"
}

fun formatNumber(number: Int): String {
    val numberString = number.toString()
    return if (numberString.length == 2) {
        numberString
    } else {
        "0$numberString"
    }
}

// "dd-MM-yyyy HH:mm:ss"
@OptIn(ExperimentalTime::class)
fun getCurrentDateTimeDisplay(): String {
    val instant = Clock.System.now()
    val datetimeSystemDefaultTimeZone = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val day = formatNumber(datetimeSystemDefaultTimeZone.day)
    val month = formatNumber(datetimeSystemDefaultTimeZone.month.number)
    val hour = formatNumber(datetimeSystemDefaultTimeZone.time.hour)
    val min = formatNumber(datetimeSystemDefaultTimeZone.time.minute)
    val second = formatNumber(datetimeSystemDefaultTimeZone.time.second)

    return "$day-$month-${datetimeSystemDefaultTimeZone.year} $hour:$min:$second"
}
