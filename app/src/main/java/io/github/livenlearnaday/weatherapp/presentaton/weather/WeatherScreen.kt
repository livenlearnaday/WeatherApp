package io.github.livenlearnaday.weatherapp.presentaton.weather

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.livenlearnaday.weatherapp.R
import io.github.livenlearnaday.weatherapp.domain.model.CurrentWeatherConditionModel
import io.github.livenlearnaday.weatherapp.domain.model.CurrentWeatherModel
import io.github.livenlearnaday.weatherapp.domain.model.LocationModel
import io.github.livenlearnaday.weatherapp.domain.model.RequestModel
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
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
            .background(Color.White)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp),
            text = stringResource(id = R.string.label_detail),
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            WeatherItemColumn(
                title = stringResource(R.string.label_humidity),
                value = getHumidityInPercent(currentWeather.currentWeatherCondition.humidity)
            )

            WeatherItemColumn(
                title = stringResource(R.string.label_pressure),
                value = "${currentWeather.currentWeatherCondition.pressure} MB"
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            WeatherItemColumn(
                title = stringResource(R.string.label_uv_index),
                value = getFormattedUVRange(currentWeather.currentWeatherCondition.uvIndex.toDouble())
            )

            WeatherItemColumn(
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
            .size(150.dp)
            .background(color = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(14.dp),
        contentColor = MaterialTheme.colorScheme.onSurface

    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(5.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth()
                    .padding(top = 10.dp),
                text = title,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = 20.dp, bottom = 10.dp),
                text = value,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview(
    showBackground = true
    //  heightDp = 360, widthDp = 800 //for quick landscape mode check
)
fun PreviewWeatherScreen() {
    WeatherAppTheme {
        WeatherScreen(
            weatherState = WeatherState(
                currentWeatherModel = currentWeather
            ),
            onWeatherAction = {},
            onBackPressed = {}
        )
    }
}

private val currentWeather = CurrentWeatherModel(
    request = RequestModel(
        type = "City",
        query = "Bangkok, Thailand",
        language = "en",
        unit = "m"
    ),
    location = LocationModel(
        name = "Bangkok",
        country = "Thailand",
        localTime = "2025-07-02 20:00"
    ),
    currentWeatherCondition = CurrentWeatherConditionModel(
        temperature = 25.0,
        windSpeed = 9,
        windDegree = 249,
        windDir = "WSW",
        pressure = 1008,
        humidity = 100,
        uvIndex = 0
    )
)
