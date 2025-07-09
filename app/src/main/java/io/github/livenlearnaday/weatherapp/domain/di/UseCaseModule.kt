package io.github.livenlearnaday.weatherapp.domain.di

import io.github.livenlearnaday.weatherapp.data.repository.WeatherRepository
import io.github.livenlearnaday.weatherapp.domain.usecase.FetchWeatherFromApiUseCase
import io.github.livenlearnaday.weatherapp.domain.usecase.FetchWeatherFromApiUseCaseImp
import io.github.livenlearnaday.weatherapp.domain.usecase.ValidateUserInputUseCase
import io.github.livenlearnaday.weatherapp.domain.usecase.ValidateUserInputUseCaseImp
import org.koin.dsl.module

val useCaseModule = module {

    factory<FetchWeatherFromApiUseCase> {
        FetchWeatherFromApiUseCaseImp(
            get<WeatherRepository>()
        )
    }

    factory<ValidateUserInputUseCase> {
        ValidateUserInputUseCaseImp()
    }
}
