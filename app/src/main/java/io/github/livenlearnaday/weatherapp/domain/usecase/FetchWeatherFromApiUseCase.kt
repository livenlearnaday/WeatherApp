package io.github.livenlearnaday.weatherapp.domain.usecase

import io.github.livenlearnaday.weatherapp.domain.CheckResult
import io.github.livenlearnaday.weatherapp.domain.DataError
import io.github.livenlearnaday.weatherapp.domain.model.CurrentWeatherModel
import io.github.livenlearnaday.weatherapp.domain.model.ErrorResponseModel

fun interface FetchWeatherFromApiUseCase {
    suspend fun execute(nameArg: String): CheckResult<CurrentWeatherModel, DataError.Network, ErrorResponseModel>
}
