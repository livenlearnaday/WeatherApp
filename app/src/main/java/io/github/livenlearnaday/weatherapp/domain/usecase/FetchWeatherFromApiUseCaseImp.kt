package io.github.livenlearnaday.domain.survey.usecases.imp

import io.github.livenlearnaday.weatherapp.data.repository.WeatherRepository
import io.github.livenlearnaday.weatherapp.domain.CheckResult
import io.github.livenlearnaday.weatherapp.domain.DataError
import io.github.livenlearnaday.weatherapp.domain.model.CurrentWeatherModel
import io.github.livenlearnaday.weatherapp.domain.model.ErrorResponseModel
import io.github.livenlearnaday.weatherapp.domain.usecase.FetchWeatherFromApiUseCase

class FetchWeatherFromApiUseCaseImp(
    private val weatherRepository: WeatherRepository
) : FetchWeatherFromApiUseCase {
    override suspend fun execute(nameArg: String): CheckResult<CurrentWeatherModel, DataError.Network, ErrorResponseModel> = weatherRepository.fetchWeatherFromApi(nameArg)
}
