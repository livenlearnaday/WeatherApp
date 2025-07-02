package io.github.livenlearnaday.weatherapp.presentaton.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import io.github.livenlearnaday.weatherapp.presentaton.util.validateInput
import timber.log.Timber

class HomeViewModel : ViewModel() {

    companion object {
        @JvmStatic
        private val TAG = HomeViewModel::class.java.simpleName
    }

    var homeState by mutableStateOf(HomeState())
        private set

    init {
        Timber.tag(TAG)
    }

    fun homeAction(action: HomeAction) {
        when (action) {
            HomeAction.OnClickedSearch -> {
                homeState = homeState.copy(
                    isLoading = true
                )
                validateInput()
            }

            HomeAction.ResetMessage -> {
                if (homeState.isError) {
                    homeState = homeState.copy(
                        isError = false,
                        errorMessage = ""
                    )
                } else {
                    homeState = homeState.copy(
                        toastMessage = ""
                    )
                }
            }

            HomeAction.ResetNavigateToWeather -> {
                homeState = homeState.copy(
                    shouldNavigateToWeather = false
                )
            }
        }
    }

    private fun validateInput() {
        val input = homeState.nameTextFieldState.text.toString()
        when {
            input.isBlank() -> {
                homeState = homeState.copy(
                    toastMessage = "Please input city name",
                    isLoading = false
                )
            }

            else -> {
                when {
                    input.validateInput().isEmpty() -> {
                        homeState = homeState.copy(
                            shouldNavigateToWeather = true,
                            isLoading = false
                        )
                    }

                    else -> {
                        homeState = homeState.copy(
                            toastMessage = "Please input valid city name",
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun resetHomeState() {
        homeState = homeState.copy(
            shouldNavigateToWeather = false,
            isLoading = false,
            toastMessage = "",
            isError = false,
            errorMessage = ""
        )
    }

    override fun onCleared() {
        resetHomeState()
        super.onCleared()
    }
}
