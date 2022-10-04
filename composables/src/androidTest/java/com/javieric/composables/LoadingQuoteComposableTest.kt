package com.javieric.composables

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import com.javieric.composables.theme.RonSwansonQuotesTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LoadingQuoteComposableTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testWhenStatusIsLoadingThenLoadingIndicatorIsDisplayed() {

        composeTestRule.setContent {
            RonSwansonQuotesTheme {
                LoadingQuoteComposable()
            }
        }

        composeTestRule.onNode(hasTestTag("loadingIndicator"))
            .assertIsDisplayed()
    }
}