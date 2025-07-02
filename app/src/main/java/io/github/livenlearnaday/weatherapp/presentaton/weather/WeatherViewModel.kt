package io.github.livenlearnaday.weatherapp.presentaton.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.livenlearnaday.weatherapp.domain.CheckResult
import io.github.livenlearnaday.weatherapp.domain.model.CurrentWeatherModel
import io.github.livenlearnaday.weatherapp.domain.usecase.FetchWeatherFromApiUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber

class WeatherViewModel(
    private val nameArg: String,
    private val fetchWeatherFromApiUseCase: FetchWeatherFromApiUseCase
) : ViewModel() {

    companion object {
        @JvmStatic
        private val TAG = WeatherViewModel::class.java.simpleName
    }

    private val defaultExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
    }

    var weatherState by mutableStateOf(WeatherState())
        private set

    init {
        Timber.tag(TAG)
        fetchData()
    }

    fun weatherAction(action: WeatherAction) {
        when (action) {
            WeatherAction.ResetErrorMessage -> {
                weatherState = weatherState.copy(
                    isError = false,
                    errorMessage = ""
                )
            }
        }
    }

    private fun fetchData() {
        weatherState = weatherState.copy(
            isLoading = true
        )
        viewModelScope.launch(defaultExceptionHandler) {
            when (val apiResponse = fetchWeatherFromApiUseCase.execute(nameArg)) {
                is CheckResult.Success -> {
                    weatherState = weatherState.copy(
                        isLoading = false,
                        currentWeatherModel = apiResponse.data
                    )
                }

                is CheckResult.Failure -> {
                    weatherState = weatherState.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = apiResponse.responseError?.error?.message
                            ?: ""
                    )
                }
            }
        }
    }

    private fun resetWeatherState() {
        weatherState = weatherState.copy(
            isLoading = false,
            currentWeatherModel = CurrentWeatherModel(),
            isError = false,
            errorMessage = ""
        )
    }

    override fun onCleared() {
        resetWeatherState()
        super.onCleared()
    }
}
