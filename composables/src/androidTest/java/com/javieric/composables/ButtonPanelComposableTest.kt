package com.javieric.composables

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.javieric.composables.theme.RonSwansonQuotesTheme
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
            image = androidx.appcompat.R.drawable.abc_ic_menu_share_mtrl_alpha
        ) { }

        val copyMenuIcon = ButtonPanelItem(
            title = "Copy",
            image = androidx.appcompat.R.drawable.abc_ic_menu_cut_mtrl_alpha
        ) { }

        val shareMenuIcon = ButtonPanelItem(
            title = "Share",
            image = androidx.appcompat.R.drawable.abc_ic_menu_copy_mtrl_am_alpha
        ) { }

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
            image = androidx.appcompat.R.drawable.abc_ic_menu_copy_mtrl_am_alpha
        ) { buttonClicked = true }

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