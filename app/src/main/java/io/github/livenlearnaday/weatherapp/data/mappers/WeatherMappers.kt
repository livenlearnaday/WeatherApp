package io.github.livenlearnaday.weatherapp.data.mappers

import io.github.livenlearnaday.weatherapp.data.models.CurrentWeatherCondition
import io.github.livenlearnaday.weatherapp.data.models.CurrentWeatherResponse
import io.github.livenlearnaday.weatherapp.data.models.Location
import io.github.livenlearnaday.weatherapp.data.models.OpenWeatherCurrentWeatherResponse
import io.github.livenlearnaday.weatherapp.data.models.Request
import io.github.livenlearnaday.weatherapp.domain.model.CurrentWeatherConditionModel
import io.github.livenlearnaday.weatherapp.domain.model.CurrentWeatherModel
import io.github.livenlearnaday.weatherapp.domain.model.LocationModel
import io.github.livenlearnaday.weatherapp.domain.model.RequestModel
import io.github.livenlearnaday.weatherapp.presentaton.util.getCurrentDateTimeDisplay
import io.github.livenlearnaday.weatherapp.presentaton.util.getOpenWeatherIconUrl
import io.github.livenlearnaday.weatherapp.presentaton.util.getWindDirection

fun CurrentWeatherResponse.toCurrentWeatherModel(): CurrentWeatherModel = CurrentWeatherModel(
    request = this.request?.toRequestModel() ?: RequestModel(),
    location = this.location?.toLocationModel() ?: LocationModel(),
    currentWeatherCondition = this.currentWeatherCondition?.toCurrentWeatherConditionModel() ?: CurrentWeatherConditionModel()
)

fun Request.toRequestModel(): RequestModel = RequestModel(
    type = this.type,
    query = this.query,
    language = this.language,
    unit = this.unit
)

fun Location.toLocationModel(): LocationModel = LocationModel(
    name = this.name,
    country = this.country,
    region = this.region,
    lat = this.lat,
    lng = this.lng,
    timeZoneId = this.timezoneId,
    localTime = this.localtime,
    localTimeEpoch = this.localtimeEpoch,
    utcOffset = this.utcOffset,
    currentSystemDateTimeDisplay = getCurrentDateTimeDisplay()
)

fun CurrentWeatherCondition.toCurrentWeatherConditionModel(): CurrentWeatherConditionModel = CurrentWeatherConditionModel(
    observationTime = this.observationTime,
    temperature = this.temperature,
    weatherCode = this.weatherCode,
    weatherIcons = this.weatherIcons,
    weatherDescriptions = this.weatherDescriptions,
    windSpeed = this.windSpeed,
    windDegree = this.windDegree,
    windDir = this.windDir,
    pressure = this.pressure,
    precip = this.precip,
    humidity = this.humidity,
    cloudCover = this.cloudCover,
    feelsLike = this.feelsLike.toDouble(),
    uvIndex = this.uvIndex.toDouble(),
    visibility = this.visibility,
    isDay = this.isDay
)

fun OpenWeatherCurrentWeatherResponse.toCurrentWeatherModel(): CurrentWeatherModel = CurrentWeatherModel(
    location = LocationModel(
        name = this.name,
        country = this.sysResponse.country,
        lat = this.coordinates.lat,
        lng = this.coordinates.lng,
        currentSystemDateTimeDisplay = getCurrentDateTimeDisplay()
    ),
    currentWeatherCondition = CurrentWeatherConditionModel(
        temperature = this.mainResponse.temp,
        feelsLike = this.mainResponse.feelsLike,
        humidity = this.mainResponse.humidity,
        visibility = this.visibility,
        windSpeed = this.windResponse.windSpeed.toInt(),
        windDir = getWindDirection(this.windResponse.windDegree),
        windDegree = this.windResponse.windDegree,
        weatherIcons = listOf(getOpenWeatherIconUrl(this.openWeatherResponseList.first().icon)),
        pressure = this.mainResponse.pressure,
        uvIndex = -99.0
    )
)
