package io.github.livenlearnaday.weatherapp.data.remote

import io.github.livenlearnaday.weatherapp.BuildConfig

object ApiRoutes {
    const val WEATHERSTACK_BASE_URL = BuildConfig.WEATHERSTACK_ENDPOINT
    const val WEATHERSTACK_ACCESS_KEY = BuildConfig.WEATHERSTACK_KEY

    const val OPENWEATHER_BASE_URL = BuildConfig.OPENWEATHER_ENDPOINT
    const val OPENWEATHER_ACCESS_KEY = BuildConfig.OPENWEATHER_KEY
}
