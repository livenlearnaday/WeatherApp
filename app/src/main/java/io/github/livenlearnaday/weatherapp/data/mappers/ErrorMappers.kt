package io.github.livenlearnaday.weatherapp.data.mappers

import io.github.livenlearnaday.weatherapp.data.models.ErrorDto
import io.github.livenlearnaday.weatherapp.data.models.ErrorResponseDto
import io.github.livenlearnaday.weatherapp.domain.model.ErrorModel
import io.github.livenlearnaday.weatherapp.domain.model.ErrorResponseModel

fun ErrorResponseDto.toErrorResponseModel(): ErrorResponseModel = ErrorResponseModel(
    success = this.success,
    error = this.error.toErrorModel()
)

fun ErrorDto.toErrorModel(): ErrorModel = ErrorModel(
    code = this.code,
    type = this.type,
    message = this.message
)
