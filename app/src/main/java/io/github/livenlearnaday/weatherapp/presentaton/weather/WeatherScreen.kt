package io.github.livenlearnaday.weatherapp.presentaton.weather

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import io.github.livenlearnaday.weatherapp.R
import io.github.livenlearnaday.weatherapp.domain.model.CurrentWeatherModel
import io.github.livenlearnaday.weatherapp.presentaton.util.getFormattedUVRange
import io.github.livenlearnaday.weatherapp.presentaton.util.getHumidityInPercent
import io.github.livenlearnaday.weatherapp.presentaton.util.getUVIndexColor
import io.github.livenlearnaday.weatherapp.ui.components.CommonAlertDialog
import io.github.livenlearnaday.weatherapp.ui.components.CustomImage
import io.github.livenlearnaday.weatherapp.ui.components.CustomTopAppBar
import io.github.livenlearnaday.weatherapp.ui.components.DotPulsingLoadingIndicator
import io.github.livenlearnaday.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    weatherState: WeatherState,
    onWeatherAction: (WeatherAction) -> Unit,
    onBackPressed: () -> Unit
) {
    BackHandler(enabled = true, onBack = {
        onBackPressed()
    })

    if (weatherState.isError) {
        CommonAlertDialog(
            onClose = {
                onWeatherAction(WeatherAction.ResetErrorMessage)
                onBackPressed()
            },
            onConfirm = {
                onWeatherAction(WeatherAction.ResetErrorMessage)
                onBackPressed()
            },
            dialogText = weatherState.errorMessage.ifEmpty { stringResource(R.string.error_general) }
        )
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            CustomTopAppBar(
                title = stringResource(R.string.current_weather),
                showBackArrow = true,
                onBackPressed = {
                    onBackPressed()
                }
            )
        },
        content = { innerPadding ->

            if (weatherState.isLoading) {
                DotPulsingLoadingIndicator()
            } else {
                Column(
                    modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TopUI(
                        currentWeather = weatherState.currentWeatherModel
                    )

                    BottomUI(
                        currentWeather = weatherState.currentWeatherModel
                    )
                }
            }
        }
    )
}

@Composable
fun TopUI(
    currentWeather: CurrentWeatherModel
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth(1f)
            .padding(horizontal = 10.dp, vertical = 20.dp)

    ) {
        Text(
            modifier = Modifier
                .testTag("city")
                .padding(bottom = 5.dp),
            text = currentWeather.location.name,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            modifier = Modifier
                .testTag("country")
                .padding(bottom = 20.dp),
            text = currentWeather.location.country,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier
                .testTag("localTime")
                .padding(bottom = 30.dp),
            text = currentWeather.location.localTime,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelLarge
        )

        Text(
            modifier = Modifier
                .testTag("temperature")
                .padding(bottom = 20.dp),
            text = "${currentWeather.currentWeatherCondition.temperature} ${
                stringResource(
                    R.string.label_degree_celsius
                )
            }",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        CustomImage(
            modifier = Modifier.size(128.dp),
            urlString = currentWeather.currentWeatherCondition.weatherIcons.firstOrNull() ?: "",
            contentDescription = stringResource(R.string.label_weather_icon)

        )

        OutlinedButton(
            onClick = {},
            modifier = Modifier
                .padding(20.dp)
                .size(50.dp),
            shape = CircleShape,
            border = BorderStroke(5.dp, Transparent),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = getUVIndexColor(
                    getFormattedUVRange(
                        currentWeather.currentWeatherCondition.uvIndex.toDouble()
                    )
                )
            ),
            content = {}
        )
    }
}

@Composable
fun BottomUI(
    currentWeather: CurrentWeatherModel
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp)
            .background(Color.White)
            .clip(RoundedCornerShape(8.dp))

    ) {
        ConstraintLayout {
            val (
                detail,
                humidity,
                pressure,
                uv,
                wind
            ) = createRefs()

            Text(
                modifier = Modifier
                    .constrainAs(detail) {
                        top.linkTo(parent.top, margin = 10.dp)
                        bottom.linkTo(humidity.top, margin = 10.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                text = stringResource(id = R.string.label_detail),
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )

            WeatherItemColumn(
                modifier = Modifier
                    .constrainAs(humidity) {
                        top.linkTo(detail.bottom, margin = 20.dp)
                        bottom.linkTo(uv.top, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(pressure.start)
                    },
                title = stringResource(R.string.label_humidity),
                value = getHumidityInPercent(currentWeather.currentWeatherCondition.humidity)
            )

            WeatherItemColumn(
                modifier = Modifier
                    .constrainAs(pressure) {
                        top.linkTo(humidity.top)
                        bottom.linkTo(humidity.bottom)
                        start.linkTo(humidity.end, margin = 20.dp)
                        end.linkTo(parent.end)
                    },
                title = stringResource(R.string.label_pressure),
                value = currentWeather.currentWeatherCondition.pressure.toString()
            )

            WeatherItemColumn(
                modifier = Modifier
                    .constrainAs(uv) {
                        top.linkTo(humidity.bottom, margin = 20.dp)
                        start.linkTo(humidity.start)
                        bottom.linkTo(parent.bottom, margin = 20.dp)
                    },
                title = stringResource(R.string.label_uv_index),
                value = getFormattedUVRange(currentWeather.currentWeatherCondition.uvIndex.toDouble())
            )

            WeatherItemColumn(
                modifier = Modifier
                    .constrainAs(wind) {
                        top.linkTo(uv.top)
                        bottom.linkTo(uv.bottom)
                        start.linkTo(pressure.start)
                        end.linkTo(parent.end)
                    },
                title = stringResource(R.string.label_wind_speed),
                value = "${currentWeather.currentWeatherCondition.windSpeed} km/h ${currentWeather.currentWeatherCondition.windDir}"
            )
        }
    }
}

@Composable
fun WeatherItemColumn(
    modifier: Modifier = Modifier,
    title: String,
    value: String
) {
    Surface(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(20.dp),
        shape = RoundedCornerShape(14.dp),
        contentColor = MaterialTheme.colorScheme.onSurface

    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(bottom = 5.dp),
                text = title,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = value
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewHomeScreen() {
    WeatherAppTheme {
        WeatherScreen(
            weatherState = WeatherState(),
            onWeatherAction = {},
            onBackPressed = {}
        )
    }
}
