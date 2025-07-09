package io.github.livenlearnaday.weatherapp.presentaton.home

import androidx.compose.foundation.text.input.TextFieldState

data class HomeState(
    val isLoading: Boolean = false,
    var nameTextFieldState: TextFieldState = TextFieldState(),
    val isValidCityName: Boolean = false,
    val toastMessage: String = "",
    val isError: Boolean = false,
    val errorMessage: String = "",
    val shouldNavigateToWeather: Boolean = false,
    val weatherProviderTypeName: String = ""

)
