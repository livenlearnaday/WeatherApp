package io.github.livenlearnaday.weatherapp.presentaton.home

sealed interface HomeAction {

    data object OnClickedWeatherStack : HomeAction
    data object ResetMessage : HomeAction
    data object ResetWeatherNavigation : HomeAction
    data object OnClickedOpenWeather : HomeAction
}
