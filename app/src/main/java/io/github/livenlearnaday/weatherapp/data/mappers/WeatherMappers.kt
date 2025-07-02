package io.github.livenlearnaday.weatherapp.data.mappers

import io.github.livenlearnaday.weatherapp.data.models.CurrentWeatherCondition
import io.github.livenlearnaday.weatherapp.data.models.CurrentWeatherResponse
import io.github.livenlearnaday.weatherapp.data.models.Location
import io.github.livenlearnaday.weatherapp.data.models.Request
import io.github.livenlearnaday.weatherapp.domain.model.CurrentWeatherConditionModel
import io.github.livenlearnaday.weatherapp.domain.model.CurrentWeatherModel
import io.github.livenlearnaday.weatherapp.domain.model.LocationModel
import io.github.livenlearnaday.weatherapp.domain.model.RequestModel

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
    utcOffset = this.utcOffset
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
    feelsLike = this.feelsLike,
    uvIndex = this.uvIndex,
    visibility = this.visibility,
    isDay = this.isDay
)
