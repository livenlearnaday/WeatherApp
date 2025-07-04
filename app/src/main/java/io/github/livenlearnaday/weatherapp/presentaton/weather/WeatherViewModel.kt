package io.github.livenlearnaday.weatherapp.presentaton.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.livenlearnaday.weatherapp.domain.CheckResult
import io.github.livenlearnaday.weatherapp.domain.model.CurrentWeatherModel
import io.github.livenlearnaday.weatherapp.domain.usecase.FetchWeatherFromApiUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState = _weatherState.asStateFlow()

    init {
        Timber.tag(TAG)
        fetchData()
    }

    fun weatherAction(action: WeatherAction) {
        when (action) {
            WeatherAction.ResetErrorMessage -> {
                _weatherState.value = _weatherState.value.copy(
                    isError = false,
                    errorMessage = ""
                )
            }
        }
    }

    private fun fetchData() {
        _weatherState.value = _weatherState.value.copy(
            isLoading = true
        )
        viewModelScope.launch(defaultExceptionHandler) {
            when (val apiResponse = fetchWeatherFromApiUseCase.execute(nameArg)) {
                is CheckResult.Success -> {
                    _weatherState.value = _weatherState.value.copy(
                        isLoading = false,
                        currentWeatherModel = apiResponse.data
                    )
                }

                is CheckResult.Failure -> {
                    _weatherState.value = _weatherState.value.copy(
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
        _weatherState.value = _weatherState.value.copy(
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
