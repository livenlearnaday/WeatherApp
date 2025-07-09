package io.github.livenlearnaday.weatherapp.presentaton.weather

import io.github.livenlearnaday.weatherapp.MockData
import io.github.livenlearnaday.weatherapp.data.models.WeatherProviderType
import io.github.livenlearnaday.weatherapp.domain.CheckResult
import io.github.livenlearnaday.weatherapp.domain.model.CurrentWeatherModel
import io.github.livenlearnaday.weatherapp.domain.usecase.FetchWeatherFromApiUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WeatherViewModelTest {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var currentWeather: CurrentWeatherModel

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var fetchWeatherFromApiUseCase: FetchWeatherFromApiUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        currentWeather = MockData.currentWeather
        viewModel = WeatherViewModel(
            currentWeather.location.name,
            WeatherProviderType.WEATHERSTACK.name,
            fetchWeatherFromApiUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun test_fetch_data_from_network_when_success() {
        // Arrange
        coEvery { fetchWeatherFromApiUseCase.execute(currentWeather.location.name, any()) } returns CheckResult.Success(
            currentWeather
        )

        // Act

        // Assert
        if (!viewModel.weatherState.value.isLoading) {
            assertEquals(currentWeather, viewModel.weatherState.value.currentWeatherModel)
        }
    }
}
