package io.github.livenlearnaday.weatherapp.domain.usecase

import io.github.livenlearnaday.weatherapp.MockData
import io.github.livenlearnaday.weatherapp.data.models.WeatherProviderType
import io.github.livenlearnaday.weatherapp.data.repository.WeatherRepository
import io.github.livenlearnaday.weatherapp.domain.CheckResult
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
        val weather = MockData.currentWeather
        coEvery { weatherRepository.fetchWeatherFromWeatherStackApi(weather.location.name) } returns CheckResult.Success(weather)
        val fetchWeatherFromApiUseCaseImp = FetchWeatherFromApiUseCaseImp(weatherRepository)

        runTest {
            // Act
            val result = fetchWeatherFromApiUseCaseImp.execute(weather.location.name, WeatherProviderType.WEATHERSTACK)

            // Assert
            assertEquals(CheckResult.Success(weather), result)
        }
    }
}
