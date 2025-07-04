package io.github.livenlearnaday.weatherapp.presentaton.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.livenlearnaday.weatherapp.domain.usecase.ValidateUserInputUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import timber.log.Timber

class HomeViewModel(
    private val validateUserInputUseCase: ValidateUserInputUseCase
) : ViewModel() {

    companion object {
        @JvmStatic
        private val TAG = HomeViewModel::class.java.simpleName
    }

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    init {
        Timber.tag(TAG)
    }

    fun homeAction(action: HomeAction) {
        when (action) {
            HomeAction.OnClickedSearch -> {
                validateInput()
            }

            HomeAction.ResetMessage -> {
                if (_homeState.value.isError) {
                    _homeState.value = _homeState.value.copy(
                        isError = false,
                        errorMessage = ""
                    )
                } else {
                    _homeState.value = _homeState.value.copy(
                        toastMessage = ""
                    )
                }
            }

            HomeAction.ResetWeatherNavigation -> {
                _homeState.value = _homeState.value.copy(
                    shouldNavigateToWeather = false,
                    isLoading = false
                )
            }
        }
    }

    private fun validateInput() {
        validateUserInputUseCase.execute(_homeState.value.nameTextFieldState.text.toString())
            .onStart {
                _homeState.value = _homeState.value.copy(
                    isLoading = true
                )
            }
            .catch {
                _homeState.value = _homeState.value.copy(
                    isLoading = false,
                    isError = true
                )
            }
            .distinctUntilChanged()
            .onEach { result ->
                when (result) {
                    "" -> {
                        _homeState.update { homeState ->
                            homeState.copy(
                                shouldNavigateToWeather = true
                            )
                        }
                    }

                    else -> {
                        _homeState.value = _homeState.value.copy(
                            toastMessage = result,
                            isLoading = false
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun resetHomeState() {
        _homeState.value = _homeState.value.copy(
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
