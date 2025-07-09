package io.github.livenlearnaday.weatherapp

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import io.github.livenlearnaday.weatherapp.domain.model.CurrentWeatherConditionModel
import io.github.livenlearnaday.weatherapp.domain.model.CurrentWeatherModel
import io.github.livenlearnaday.weatherapp.domain.model.LocationModel
import io.github.livenlearnaday.weatherapp.domain.model.RequestModel
import io.github.livenlearnaday.weatherapp.presentaton.weather.WeatherScreen
import io.github.livenlearnaday.weatherapp.presentaton.weather.WeatherState
import org.junit.Rule
import org.junit.Test

class WeatherScreenTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun should_show_weather_detail_top_ui() {
        // Arrange
        val weather = CurrentWeatherModel(
            request = RequestModel(
                type = "City",
                query = "Bangkok, Thailand",
                language = "en",
                unit = "m"
            ),
            location = LocationModel(
                name = "Bangkok",
                country = "Thailand",
                localTime = "2025-07-02 00:51",
                currentSystemDateTimeDisplay = "02-07-2025 20:00:00"
            ),
            currentWeatherCondition = CurrentWeatherConditionModel(
                temperature = 25.0,
                windSpeed = 9,
                windDegree = 249,
                windDir = "WSW",
                pressure = 1008,
                humidity = 100,
                uvIndex = 0.18
            )
        )

        rule.setContent {
            WeatherScreen(
                weatherState = WeatherState(
                    currentWeatherModel = weather
                ),
                onWeatherAction = {},
                onBackPressed = {}
            )
        }

        // Act & Assert
        rule.onNodeWithTag("city").assertExists().assertTextContains(weather.location.name)
        rule.onNodeWithTag("country").assertExists().assertTextContains(weather.location.country)
        rule.onNodeWithTag("localTime").assertExists().assertTextContains(weather.location.localTime)
        rule.onNodeWithTag("temperature").assertExists().assertTextContains("${weather.currentWeatherCondition.temperature} ℃")
    }
}
