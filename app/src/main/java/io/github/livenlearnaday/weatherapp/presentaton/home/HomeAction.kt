package io.github.livenlearnaday.weatherapp.presentaton.home

sealed interface HomeAction {

    data object OnClickedSearch : HomeAction
    data object ResetMessage : HomeAction
    data object ResetWeatherNavigation : HomeAction
}
