package io.github.livenlearnaday.weatherapp.domain.usecase

import io.github.livenlearnaday.domain.survey.usecases.imp.ValidateUserInputUseCaseImp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ValidateUserInputUseCaseImpTest {

    @Test
    fun test_execute_should_return_expected_result_when_executed() {
        // Arrange
        val validateUserInputUseCaseImp = ValidateUserInputUseCaseImp()
        val input1 = "Ban8912"
        val expect1 = "Please input valid city name"

        val input2 = "Bangkok"
        val expect2 = ""

        val input3 = "   "
        val expect3 = "Please input city name"

        // Act
        runTest {
            val result1 = validateUserInputUseCaseImp.execute(input1).first()
            val result2 = validateUserInputUseCaseImp.execute(input2).first()
            val result3 = validateUserInputUseCaseImp.execute(input3).first()

            // Assert
            assertEquals(expect1, result1)
            assertEquals(expect2, result2)
            assertEquals(expect3, result3)
        }
    }
}
