package io.github.livenlearnaday.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.github.livenlearnaday.weatherapp.presentaton.home.HomeAction
import io.github.livenlearnaday.weatherapp.presentaton.home.HomeScreen
import io.github.livenlearnaday.weatherapp.presentaton.home.HomeViewModel
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
            val homeState = homeViewModel.homeState.collectAsStateWithLifecycle().value

            HomeScreen(
                homeState = homeState,
                onHomeAction = homeViewModel::homeAction,
                onNavigateToWeather = {
                    homeViewModel.homeAction(HomeAction.ResetWeatherNavigation)
                    navController.navigate(NavigationRoute.WeatherRoute(homeState.nameTextFieldState.text.toString()))
                }
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
