package io.github.livenlearnaday.weatherapp.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoute {
    @Serializable
    object HomeRoute

    @Serializable
    data class WeatherRoute(
        val nameArg: String,
        val weatherProviderTypeName: String
    )
}
