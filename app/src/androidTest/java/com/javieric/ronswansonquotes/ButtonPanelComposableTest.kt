package com.javieric.ronswansonquotes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
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
internal class ButtonPanelComposableTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun testWhenCallingButtonPanelComposableAllItemsAreDisplayed() {

        val newQuoteMenuIcon = ButtonPanelItem(
            title = "New Quote",
            image = Icons.Default.Refresh,
            onClick = {  }
        )

        val copyMenuIcon = ButtonPanelItem(
            title = "Copy",
            image = Icons.Default.Edit,
            onClick = {  }
        )

        val shareMenuIcon = ButtonPanelItem(
            title = "Share",
            image = Icons.Default.Share,
            onClick = {  }
        )

        val list =  listOf(newQuoteMenuIcon, copyMenuIcon, shareMenuIcon)

        composeTestRule.setContent { 
            RonSwansonQuotesTheme {
                ButtonPanelComposable(items = list)
            }
        }

        composeTestRule.onNodeWithTag("icon_${list[0].title}", useUnmergedTree = true)
            .assertExists()
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag("icon_${list[1].title}", useUnmergedTree = true)
            .assertExists()
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag("icon_${list[2].title}", useUnmergedTree = true)
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun testWhenCallingButtonPanelComposableOnClickWorks() {

        var buttonClicked = false

        val shareMenuIcon = ButtonPanelItem(
            title = "Share",
            image = Icons.Default.Share,
            onClick = { buttonClicked = true }
        )

        val list = listOf(shareMenuIcon)

        composeTestRule.setContent {
            RonSwansonQuotesTheme {
                ButtonPanelComposable(items = list)
            }
        }

        composeTestRule.onNodeWithTag("iconButton_${list[0].title}", useUnmergedTree = true)
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assert(buttonClicked)
    }
}