package io.github.livenlearnaday.weatherapp.domain.usecase

import io.github.livenlearnaday.weatherapp.data.models.WeatherProviderType
import io.github.livenlearnaday.weatherapp.domain.CheckResult
import io.github.livenlearnaday.weatherapp.domain.DataError
import io.github.livenlearnaday.weatherapp.domain.model.CurrentWeatherModel
import io.github.livenlearnaday.weatherapp.domain.model.ErrorResponseModel

fun interface FetchWeatherFromApiUseCase {
    suspend fun execute(name: String, weatherProviderType: WeatherProviderType): CheckResult<CurrentWeatherModel, DataError.Network, ErrorResponseModel>
}
