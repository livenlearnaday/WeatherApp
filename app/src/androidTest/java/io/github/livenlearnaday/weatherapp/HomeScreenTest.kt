package io.github.livenlearnaday.weatherapp

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import io.github.livenlearnaday.weatherapp.presentaton.home.HomeScreen
import io.github.livenlearnaday.weatherapp.presentaton.home.HomeState
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun should_show_input_field_and_search_button() {
        // Arrange
        rule.setContent {
            HomeScreen(
                homeState = HomeState(),
                onHomeAction = {},
                onNavigateToWeather = {}
            )
        }

        // Act & Assert
        rule.onNodeWithTag("inputText").assertExists()
        rule.onNodeWithText("Search").assertExists()
    }

    @Test
    fun should_register_expected_text_input() {
        // Arrange
        rule.setContent {
            HomeScreen(
                homeState = HomeState(),
                onHomeAction = {},
                onNavigateToWeather = {}
            )
        }

        val city = "Bangkok"

        // Act & Assert
        rule.onNodeWithTag("inputText").assertExists()
        rule.onNodeWithText("Search").assertExists()
        rule.onNodeWithTag("inputText").performTextInput(city)
        rule.onNodeWithText(city).isDisplayed()
    }
}
