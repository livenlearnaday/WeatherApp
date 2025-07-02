package io.github.livenlearnaday.weatherapp.data.di

import io.github.livenlearnaday.weatherapp.data.network_clients.HttpKtorClient
import io.github.livenlearnaday.weatherapp.data.remote.WeatherRemoteDataSource
import io.github.livenlearnaday.weatherapp.data.remote.WeatherRemoteDataSourceImp
import io.github.livenlearnaday.weatherapp.data.repository.WeatherRepository
import io.github.livenlearnaday.weatherapp.data.repository.WeatherRepositoryImp
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkModule = module {

    single<HttpClient> { _ ->
        HttpKtorClient().build()
    }

    factory<WeatherRemoteDataSource> {
        WeatherRemoteDataSourceImp(
            get<HttpClient>()
        )
    }

    factory<WeatherRepository> {
        WeatherRepositoryImp(
            get<WeatherRemoteDataSource>()
        )
    }
}
