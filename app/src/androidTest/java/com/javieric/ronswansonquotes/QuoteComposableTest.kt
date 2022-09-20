package com.javieric.ronswansonquotes

import androidx.compose.ui.test.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import androidx.compose.ui.test.junit4.createComposeRule
import com.javieric.ronswansonquotes.theme.RonSwansonQuotesTheme
import org.junit.Test

@RunWith(JUnit4::class)
class QuoteComposableTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testWhenStatusIsSuccessThenTextShowsExpectedString() {

        val testQuote = "test quote"

        composeTestRule.setContent {
            RonSwansonQuotesTheme {
                QuoteComposable(quote = testQuote)
            }
        }

        composeTestRule.onNode(hasTestTag("quoteText"))
            .assertIsDisplayed()
            .assertTextEquals(testQuote)
    }
}