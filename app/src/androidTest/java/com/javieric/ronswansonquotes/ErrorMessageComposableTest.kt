package com.javieric.ronswansonquotes

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import com.javieric.ronswansonquotes.theme.RonSwansonQuotesTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ErrorMessageComposableTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testWhenStatusIsErrorThenTextShowsExpectedString() {

        val errorMessage = "error message"

        composeTestRule.setContent {
            RonSwansonQuotesTheme {
                ErrorMessageComposable(message = errorMessage)
            }
        }

        composeTestRule.onNode(hasTestTag("quoteText"))
            .assertIsDisplayed()
            .assertTextEquals(errorMessage)
    }
}