package io.github.livenlearnaday.weatherapp.presentaton.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import io.github.livenlearnaday.weatherapp.R
import io.github.livenlearnaday.weatherapp.ui.components.CustomButton
import io.github.livenlearnaday.weatherapp.ui.components.CustomTextField
import io.github.livenlearnaday.weatherapp.ui.components.CustomTopAppBar
import io.github.livenlearnaday.weatherapp.ui.components.DotPulsingLoadingIndicator
import io.github.livenlearnaday.weatherapp.ui.components.positionAwareImePadding
import io.github.livenlearnaday.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun HomeScreen(
    homeState: HomeState,
    onHomeAction: (HomeAction) -> Unit,
    onNavigateToWeather: () -> Unit
) {
    val context = LocalContext.current
    val keyboard = LocalSoftwareKeyboardController.current

    if (homeState.isError || homeState.toastMessage.isNotEmpty()) {
        val text = when {
            homeState.isError -> homeState.errorMessage.ifEmpty { stringResource(R.string.error_general) }
            homeState.toastMessage.isNotEmpty() -> homeState.toastMessage
            else -> stringResource(R.string.error_general)
        }
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        ).show()

        onHomeAction(HomeAction.ResetMessage)
    }

    if (homeState.shouldNavigateToWeather) {
        onNavigateToWeather()
    }

    Scaffold(
        modifier = Modifier.positionAwareImePadding(),
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            CustomTopAppBar(
                title = stringResource(R.string.app_name),
                onBackPressed = { }
            )
        },
        content = { innerPadding ->

            if (homeState.isLoading) {
                DotPulsingLoadingIndicator()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomTextField(
                        modifier = Modifier
                            .testTag("inputText")
                            .padding(
                                top = innerPadding.calculateTopPadding().plus(80.dp),
                                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr).plus(30.dp),
                                end = innerPadding.calculateEndPadding(LayoutDirection.Ltr).plus(30.dp)
                            ),
                        state = homeState.nameTextFieldState,
                        hint = stringResource(R.string.label_city_name)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    CustomButton(
                        modifier = Modifier.padding(horizontal = 50.dp),
                        enableButton = !homeState.isLoading,
                        label = stringResource(R.string.label_weatherstack),
                        onButtonClicked = {
                            keyboard?.hide()
                            onHomeAction(HomeAction.OnClickedWeatherStack)
                        }
                    )

                    CustomButton(
                        modifier = Modifier.padding(top = 20.dp, start = 50.dp, end = 50.dp),
                        enableButton = !homeState.isLoading,
                        label = stringResource(R.string.label_openweather),
                        onButtonClicked = {
                            keyboard?.hide()
                            onHomeAction(HomeAction.OnClickedOpenWeather)
                        }
                    )
                }
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewHomeScreen() {
    WeatherAppTheme {
        HomeScreen(
            homeState = HomeState(),
            onHomeAction = {},
            onNavigateToWeather = {}
        )
    }
}
