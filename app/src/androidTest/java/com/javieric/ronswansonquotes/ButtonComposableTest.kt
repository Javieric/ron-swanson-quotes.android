package com.javieric.ronswansonquotes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.javieric.ronswansonquotes.theme.RonSwansonQuotesTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
internal class ButtonComposableTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testWhenClickingOnAButtonComposableTheOnClickFunctionIsCalled() {

        var iconClicked = false

        val title = "New Quote"
        composeTestRule.setContent {
            RonSwansonQuotesTheme {
                ButtonComposable(
                    title = "New Quote",
                    image = Icons.Default.Refresh,
                    onClick = { iconClicked = true }
                )
            }
        }

        composeTestRule.onNodeWithTag("iconButton_$title")
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assert(iconClicked)
    }
}