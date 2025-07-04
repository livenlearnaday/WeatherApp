package io.github.livenlearnaday.domain.survey.usecases.imp

import io.github.livenlearnaday.weatherapp.domain.usecase.ValidateUserInputUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ValidateUserInputUseCaseImp : ValidateUserInputUseCase {
    override fun execute(input: String): Flow<String> {
        val desiredCondition =
            "^[a-zA-Z\\u0080-\\u024F]+(?:. |-| |')*([1-9a-zA-Z\\u0080-\\u024F]+(?:. |-| |'))*[a-zA-Z\\u0080-\\u024F]*\$"

        return flowOf(
            when {
                input.isBlank() -> "Please input city name"
                input.matches(desiredCondition.toRegex()) -> ""
                else -> "Please input valid city name"
            }
        )
    }
}
