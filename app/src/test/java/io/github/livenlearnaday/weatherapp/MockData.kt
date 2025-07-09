package io.github.livenlearnaday.weatherapp

import io.github.livenlearnaday.weatherapp.domain.model.CurrentWeatherConditionModel
import io.github.livenlearnaday.weatherapp.domain.model.CurrentWeatherModel
import io.github.livenlearnaday.weatherapp.domain.model.LocationModel
import io.github.livenlearnaday.weatherapp.domain.model.RequestModel

object MockData {
    val currentWeather = CurrentWeatherModel(
        request = RequestModel(
            type = "City",
            query = "Bangkok, Thailand",
            language = "en",
            unit = "m"
        ),
        location = LocationModel(
            name = "Bangkok",
            country = "Thailand",
            localTime = "2025-07-02 20:00"
        ),
        currentWeatherCondition = CurrentWeatherConditionModel(
            temperature = 25.0,
            windSpeed = 9,
            windDegree = 249,
            windDir = "WSW",
            pressure = 1008,
            humidity = 100,
            uvIndex = 0.17
        )
    )
}
