package io.github.livenlearnaday.weatherapp.domain.usecase

import io.github.livenlearnaday.weatherapp.data.models.WeatherProviderType
import io.github.livenlearnaday.weatherapp.data.repository.WeatherRepository
import io.github.livenlearnaday.weatherapp.domain.CheckResult
import io.github.livenlearnaday.weatherapp.domain.DataError
import io.github.livenlearnaday.weatherapp.domain.model.CurrentWeatherModel
import io.github.livenlearnaday.weatherapp.domain.model.ErrorResponseModel

class FetchWeatherFromApiUseCaseImp(
    private val weatherRepository: WeatherRepository
) : FetchWeatherFromApiUseCase {
    override suspend fun execute(name: String, weatherProviderType: WeatherProviderType): CheckResult<CurrentWeatherModel, DataError.Network, ErrorResponseModel> = when (weatherProviderType) {
        WeatherProviderType.WEATHERSTACK -> weatherRepository.fetchWeatherFromWeatherStackApi(name)
        else -> weatherRepository.fetchWeatherFromOpenWeatherApi(name)
    }
}
