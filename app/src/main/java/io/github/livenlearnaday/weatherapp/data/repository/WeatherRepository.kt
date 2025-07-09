package io.github.livenlearnaday.weatherapp.data.repository

import io.github.livenlearnaday.weatherapp.domain.CheckResult
import io.github.livenlearnaday.weatherapp.domain.DataError
import io.github.livenlearnaday.weatherapp.domain.model.CurrentWeatherModel
import io.github.livenlearnaday.weatherapp.domain.model.ErrorResponseModel

interface WeatherRepository {

    suspend fun fetchWeatherFromWeatherStackApi(name: String): CheckResult<CurrentWeatherModel, DataError.Network, ErrorResponseModel>
    suspend fun fetchWeatherFromOpenWeatherApi(name: String): CheckResult<CurrentWeatherModel, DataError.Network, ErrorResponseModel>
}
