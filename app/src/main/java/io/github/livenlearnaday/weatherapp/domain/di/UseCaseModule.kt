package io.github.livenlearnaday.weatherapp.domain.di

import io.github.livenlearnaday.domain.survey.usecases.imp.FetchWeatherFromApiUseCaseImp
import io.github.livenlearnaday.weatherapp.data.repository.WeatherRepository
import io.github.livenlearnaday.weatherapp.domain.usecase.FetchWeatherFromApiUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory<FetchWeatherFromApiUseCase> {
        FetchWeatherFromApiUseCaseImp(
            get<WeatherRepository>()
        )
    }
}
