package io.github.livenlearnaday.weatherapp.presentaton.weather

sealed interface WeatherAction {
    object ResetErrorMessage : WeatherAction
}
