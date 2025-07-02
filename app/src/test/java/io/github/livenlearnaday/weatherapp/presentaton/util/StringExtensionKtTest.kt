package io.github.livenlearnaday.weatherapp.presentaton.util

import org.junit.Assert.assertEquals
import org.junit.Test

class StringExtensionKtTest {

    @Test
    fun test_validateInput_should_return_expected_result() {
        // Arrange
        val input1 = "Ban8912"
        val expect1 = "Please input valid city name"

        val input2 = "Bangkok"
        val expect2 = ""

        // Act
        val result1 = input1.validateInput()
        val result2 = input2.validateInput()

        // Assert
        assertEquals(expect1, result1)
        assertEquals(expect2, result2)
    }
}
