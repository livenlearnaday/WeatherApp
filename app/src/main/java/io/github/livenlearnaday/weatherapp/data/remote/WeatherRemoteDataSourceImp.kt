package io.github.livenlearnaday.weatherapp.data.remote

import io.github.livenlearnaday.data.util.safeApiRequest
import io.github.livenlearnaday.weatherapp.data.models.CurrentWeatherResponse
import io.github.livenlearnaday.weatherapp.data.models.ErrorResponseDto
import io.github.livenlearnaday.weatherapp.domain.CheckResult
import io.github.livenlearnaday.weatherapp.domain.DataError
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.JsonConvertException
import kotlinx.serialization.json.Json

class WeatherRemoteDataSourceImp(
    private val httpClient: HttpClient
) : WeatherRemoteDataSource {
    override suspend fun fetchWeather(nameArg: String): CheckResult<CurrentWeatherResponse, DataError.Network, ErrorResponseDto> {
        val response = httpClient
            .get(
                urlString = "${ApiRoutes.BASE_URL}${ApiRoutes.CURRENT_WEATHER}?access_key=${ApiRoutes.ACCESS_KEY}&query=$nameArg"
            )

        return try {
            val safeResponse = safeApiRequest<CurrentWeatherResponse> { response }
            safeResponse
        } catch (e: JsonConvertException) {
            val raw = response.bodyAsText(Charsets.UTF_8)
            val error = Json.decodeFromString<ErrorResponseDto>(raw)
            CheckResult.Failure(DataError.Network.UNKNOWN, error)
        }
    }
}
