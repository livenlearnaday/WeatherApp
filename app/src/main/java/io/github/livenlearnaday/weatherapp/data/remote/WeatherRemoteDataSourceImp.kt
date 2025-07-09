package io.github.livenlearnaday.weatherapp.data.remote

import io.github.livenlearnaday.data.util.safeApiRequest
import io.github.livenlearnaday.weatherapp.data.mappers.toErrorResponseDto
import io.github.livenlearnaday.weatherapp.data.models.CurrentWeatherResponse
import io.github.livenlearnaday.weatherapp.data.models.ErrorResponseDto
import io.github.livenlearnaday.weatherapp.data.models.OpenWeatherCurrentWeatherResponse
import io.github.livenlearnaday.weatherapp.data.models.OpenWeatherErrorResponseDto
import io.github.livenlearnaday.weatherapp.domain.CheckResult
import io.github.livenlearnaday.weatherapp.domain.DataError
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.JsonConvertException
import kotlinx.serialization.json.Json

class WeatherRemoteDataSourceImp(
    private val httpClient: HttpClient
) : WeatherRemoteDataSource {
    override suspend fun fetchWeatherFromWeatherStack(nameArg: String): CheckResult<CurrentWeatherResponse, DataError.Network, ErrorResponseDto> {
        val response = httpClient
            .get {
                url("${ApiRoutes.WEATHERSTACK_BASE_URL}/current")
                parameter("access_key", ApiRoutes.WEATHERSTACK_ACCESS_KEY)
                parameter("query", nameArg)
            }

        return try {
            val safeResponse = safeApiRequest<CurrentWeatherResponse> { response }
            safeResponse
        } catch (e: JsonConvertException) {
            val raw = response.bodyAsText(Charsets.UTF_8)
            val error = Json.decodeFromString<ErrorResponseDto>(raw)
            CheckResult.Failure(DataError.Network.UNKNOWN, error)
        }
    }

    override suspend fun fetchWeatherFromOpenWeather(
        name: String
    ): CheckResult<OpenWeatherCurrentWeatherResponse, DataError.Network, ErrorResponseDto> {
        val response = httpClient
            .get {
                url("${ApiRoutes.OPENWEATHER_BASE_URL}/data/2.5/weather")
                parameter("q", name)
                parameter("units", "metric")
                parameter("appid", ApiRoutes.OPENWEATHER_ACCESS_KEY)
            }

        return try {
            val safeResponse = safeApiRequest<OpenWeatherCurrentWeatherResponse> { response }
            safeResponse
        } catch (e: JsonConvertException) {
            val raw = response.bodyAsText(Charsets.UTF_8)
            val error = Json.decodeFromString<OpenWeatherErrorResponseDto>(raw)
            CheckResult.Failure(DataError.Network.UNKNOWN, error.toErrorResponseDto())
        }
    }
}
