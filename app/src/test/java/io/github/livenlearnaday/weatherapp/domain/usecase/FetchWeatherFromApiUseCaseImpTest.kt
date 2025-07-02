package io.github.livenlearnaday.weatherapp.domain.usecase

import io.github.livenlearnaday.domain.survey.usecases.imp.FetchWeatherFromApiUseCaseImp
import io.github.livenlearnaday.weatherapp.data.repository.WeatherRepository
import io.github.livenlearnaday.weatherapp.domain.CheckResult
import io.github.livenlearnaday.weatherapp.domain.model.CurrentWeatherConditionModel
import io.github.livenlearnaday.weatherapp.domain.model.CurrentWeatherModel
import io.github.livenlearnaday.weatherapp.domain.model.LocationModel
import io.github.livenlearnaday.weatherapp.domain.model.RequestModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchWeatherFromApiUseCaseImpTest {

    @MockK
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun test_execute_should_return_expected_result_when_success() {
        // Arrange
        val weather = CurrentWeatherModel(
            request = RequestModel(
                type = "City",
                query = "Bangkok, Thailand",
                language = "en",
                unit = "m"
            ),
            location = LocationModel(
                name = "Bangkok",
                country = "Thailand",
                localTime = "2025-07-02 00:51"
            ),
            currentWeatherCondition = CurrentWeatherConditionModel(
                temperature = 25.0,
                windSpeed = 9,
                windDegree = 249,
                windDir = "WSW",
                pressure = 1008,
                humidity = 100,
                uvIndex = 0
            )
        )
        coEvery { weatherRepository.fetchWeatherFromApi(weather.location.name) } returns CheckResult.Success(weather)
        val fetchWeatherFromApiUseCaseImp = FetchWeatherFromApiUseCaseImp(weatherRepository)

        runTest {
            // Act
            val result = fetchWeatherFromApiUseCaseImp.execute(weather.location.name)

            // Assert
            assertEquals(CheckResult.Success(weather), result)
        }
    }
}
