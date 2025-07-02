package io.github.livenlearnaday.weatherapp.presentaton.weather

import io.github.livenlearnaday.weatherapp.domain.model.CurrentWeatherModel

data class WeatherState(
    val isLoading: Boolean = false,
    val currentWeatherModel: CurrentWeatherModel = CurrentWeatherModel(),
    val isError: Boolean = false,
    val errorMessage: String = ""

)
