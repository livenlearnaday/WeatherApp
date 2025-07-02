package io.github.livenlearnaday.weatherapp.presentaton.home

sealed interface HomeEvent {

    data object OnNavigateToWeather : HomeEvent
}
