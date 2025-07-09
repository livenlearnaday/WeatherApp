package io.github.livenlearnaday.weatherapp.data.remote

import io.github.livenlearnaday.weatherapp.data.models.CurrentWeatherResponse
import io.github.livenlearnaday.weatherapp.data.models.ErrorResponseDto
import io.github.livenlearnaday.weatherapp.data.models.OpenWeatherCurrentWeatherResponse
import io.github.livenlearnaday.weatherapp.domain.CheckResult
import io.github.livenlearnaday.weatherapp.domain.DataError

interface WeatherRemoteDataSource {
    suspend fun fetchWeatherFromWeatherStack(nameArg: String): CheckResult<CurrentWeatherResponse, DataError.Network, ErrorResponseDto>
    suspend fun fetchWeatherFromOpenWeather(name: String): CheckResult<OpenWeatherCurrentWeatherResponse, DataError.Network, ErrorResponseDto>
}
