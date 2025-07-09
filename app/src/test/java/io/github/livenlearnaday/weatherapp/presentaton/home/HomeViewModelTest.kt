package io.github.livenlearnaday.weatherapp.presentaton.home

import io.github.livenlearnaday.weatherapp.domain.usecase.ValidateUserInputUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private val testDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var validateUserInputUseCase: ValidateUserInputUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)

        viewModel = HomeViewModel(validateUserInputUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun test_validate_input_should_trigger_ResetWeatherNavigation_when_success() {
        // Arrange
        every { validateUserInputUseCase.execute(any()) } returns flowOf("")

        runTest {
            // Act
            viewModel.homeAction(HomeAction.OnClickedWeatherStack)

            // Assert
            validateUserInputUseCase.execute("Bangkok")
                .onCompletion {
                    assertTrue(viewModel.homeState.value.shouldNavigateToWeather)
                }
        }
    }
}
