package io.github.livenlearnaday.weatherapp.data.remote

import io.github.livenlearnaday.weatherapp.BuildConfig

object ApiRoutes {
    const val BASE_URL = BuildConfig.WEATHERSTACK_ENDPOINT
    const val CURRENT_WEATHER = "/current"
    const val ACCESS_KEY = BuildConfig.WEATHERSTACK_KEY
}
