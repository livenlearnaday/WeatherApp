package io.github.livenlearnaday.weatherapp.presentaton.di

import io.github.livenlearnaday.weatherapp.domain.usecase.FetchWeatherFromApiUseCase
import io.github.livenlearnaday.weatherapp.domain.usecase.ValidateUserInputUseCase
import io.github.livenlearnaday.weatherapp.presentaton.home.HomeViewModel
import io.github.livenlearnaday.weatherapp.presentaton.weather.WeatherViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel<HomeViewModel> {
        HomeViewModel(
            get<ValidateUserInputUseCase>()
        )
    }

    viewModel<WeatherViewModel> { (nameArg: String) ->
        WeatherViewModel(
            nameArg = nameArg,
            get<FetchWeatherFromApiUseCase>()
        )
    }
}
