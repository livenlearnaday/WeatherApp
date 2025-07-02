package io.github.livenlearnaday.weatherapp.presentaton.util

fun String.validateInput(): String {
    val desiredCondition =
        "^[a-zA-Z\\u0080-\\u024F]+(?:. |-| |')*([1-9a-zA-Z\\u0080-\\u024F]+(?:. |-| |'))*[a-zA-Z\\u0080-\\u024F]*\$"

    return when {
        this.matches(desiredCondition.toRegex()) -> ""
        else -> "Please input valid city name"
    }
}
