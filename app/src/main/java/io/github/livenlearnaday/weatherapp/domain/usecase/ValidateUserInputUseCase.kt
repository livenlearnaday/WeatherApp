package io.github.livenlearnaday.weatherapp.domain.usecase

import kotlinx.coroutines.flow.Flow

fun interface ValidateUserInputUseCase {
    fun execute(input: String): Flow<String>
}
