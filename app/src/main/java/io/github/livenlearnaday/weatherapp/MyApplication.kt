package io.github.livenlearnaday.weatherapp

import android.app.Application
import io.github.livenlearnaday.weatherapp.data.di.networkModule
import io.github.livenlearnaday.weatherapp.domain.di.useCaseModule
import io.github.livenlearnaday.weatherapp.presentaton.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                networkModule,
                useCaseModule,
                uiModule
            )
        }
    }
}
