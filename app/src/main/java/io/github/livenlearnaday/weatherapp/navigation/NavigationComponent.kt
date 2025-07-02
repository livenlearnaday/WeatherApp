package io.github.livenlearnaday.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.github.livenlearnaday.weatherapp.presentaton.home.HomeAction
import io.github.livenlearnaday.weatherapp.presentaton.home.HomeEvent
import io.github.livenlearnaday.weatherapp.presentaton.home.HomeScreen
import io.github.livenlearnaday.weatherapp.presentaton.home.HomeViewModel
import io.github.livenlearnaday.weatherapp.presentaton.util.ObserveAsEvents
import io.github.livenlearnaday.weatherapp.presentaton.weather.WeatherScreen
import io.github.livenlearnaday.weatherapp.presentaton.weather.WeatherViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationRoute.HomeRoute
    ) {
        composable<NavigationRoute.HomeRoute> {
            val homeViewModel = koinViewModel<HomeViewModel>()
            val homeState = homeViewModel.homeState

            ObserveAsEvents(
                event = homeViewModel.homeEvent,
                onEvent = { homeEvent ->
                    when (homeEvent) {
                        HomeEvent.OnNavigateToWeather -> {
                            homeViewModel.homeAction(HomeAction.ResetWeatherNavigation)
                            navController.navigate(NavigationRoute.WeatherRoute(homeState.nameTextFieldState.text.toString()))
                        }
                    }
                }
            )

            HomeScreen(
                homeState = homeState,
                onHomeAction = homeViewModel::homeAction
            )
        }
        composable<NavigationRoute.WeatherRoute> { backStackEntry ->
            val nameArg = backStackEntry.toRoute<NavigationRoute.WeatherRoute>().nameArg
            val weatherViewModel =
                koinViewModel<WeatherViewModel> { parametersOf(nameArg) }
            val weatherState = weatherViewModel.weatherState

            WeatherScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                weatherState = weatherState,
                onWeatherAction = weatherViewModel::weatherAction

            )
        }
    }
}
