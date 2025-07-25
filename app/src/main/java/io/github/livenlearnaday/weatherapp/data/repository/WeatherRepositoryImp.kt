package io.github.livenlearnaday.weatherapp.data.repository

import io.github.livenlearnaday.weatherapp.data.mappers.toCurrentWeatherModel
import io.github.livenlearnaday.weatherapp.data.mappers.toErrorResponseDto
import io.github.livenlearnaday.weatherapp.data.mappers.toErrorResponseModel
import io.github.livenlearnaday.weatherapp.data.models.ErrorResponseDto
import io.github.livenlearnaday.weatherapp.data.models.OpenWeatherErrorResponseDto
import io.github.livenlearnaday.weatherapp.data.remote.WeatherRemoteDataSource
import io.github.livenlearnaday.weatherapp.domain.CheckResult
import io.github.livenlearnaday.weatherapp.domain.DataError
import io.github.livenlearnaday.weatherapp.domain.model.CurrentWeatherModel
import io.github.livenlearnaday.weatherapp.domain.model.ErrorModel
import io.github.livenlearnaday.weatherapp.domain.model.ErrorResponseModel
import io.ktor.serialization.JsonConvertException
import kotlinx.serialization.json.Json

class WeatherRepositoryImp(
    private val weatherRemoteDataSource: WeatherRemoteDataSource
) : WeatherRepository {
    override suspend fun fetchWeatherFromWeatherStackApi(name: String): CheckResult<CurrentWeatherModel, DataError.Network, ErrorResponseModel> = when (val apiResponse = weatherRemoteDataSource.fetchWeatherFromWeatherStack(name)) {
        is CheckResult.Success -> {
            val rawResponseString = apiResponse.data.toString()
            try {
                val result = apiResponse.data.toCurrentWeatherModel()
                CheckResult.Success(result)
            } catch (e: JsonConvertException) {
                val result = Json.decodeFromString<ErrorResponseDto>(rawResponseString)
                CheckResult.Failure(
                    DataError.Network.SERIALIZATION,
                    result.toErrorResponseModel()
                )
            } catch (e: Exception) {
                val result = Json.decodeFromString<ErrorResponseDto>(rawResponseString)
                CheckResult.Failure(DataError.Network.UNKNOWN, result.toErrorResponseModel())
            }
        }

        is CheckResult.Failure -> {
            CheckResult.Failure(
                exceptionError = apiResponse.exceptionError,
                responseError = apiResponse.responseError?.toErrorResponseModel()
                    ?: ErrorResponseModel(
                        success = false,
                        ErrorModel(
                            code = 0,
                            type = "",
                            message = "unknown error"
                        )
                    )
            )
        }
    }

    override suspend fun fetchWeatherFromOpenWeatherApi(
        name: String
    ): CheckResult<CurrentWeatherModel, DataError.Network, ErrorResponseModel> = when (val apiResponse = weatherRemoteDataSource.fetchWeatherFromOpenWeather(name)) {
        is CheckResult.Success -> {
            val rawResponseString = apiResponse.data.toString()
            try {
                val result = apiResponse.data.toCurrentWeatherModel()
                CheckResult.Success(result)
            } catch (e: JsonConvertException) {
                val result = Json.decodeFromString<OpenWeatherErrorResponseDto>(rawResponseString)
                CheckResult.Failure(
                    DataError.Network.SERIALIZATION,
                    result.toErrorResponseDto().toErrorResponseModel()
                )
            } catch (e: Exception) {
                val result = Json.decodeFromString<OpenWeatherErrorResponseDto>(rawResponseString)
                CheckResult.Failure(DataError.Network.UNKNOWN, result.toErrorResponseDto().toErrorResponseModel())
            }
        }

        is CheckResult.Failure -> {
            CheckResult.Failure(
                exceptionError = apiResponse.exceptionError,
                responseError = apiResponse.responseError?.toErrorResponseModel()
                    ?: ErrorResponseModel(
                        success = false,
                        ErrorModel(
                            code = 0,
                            type = "",
                            message = "unknown error"
                        )
                    )
            )
        }
    }
}
