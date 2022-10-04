package com.javieric.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.javieric.composables.theme.RonSwansonQuotesTheme

@Composable
fun ButtonPanelComposable(
    modifier: Modifier = Modifier,
    items: List<ButtonPanelItem>,
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ) {

        items.forEach {

            ButtonComposable(
                image = it.image,
                onClick = it.onClick,
                title = it.title,
            )
        }
    }
}

data class ButtonPanelItem(
    val title: String,
    val image: ImageVector,
    val onClick: () -> Unit,
)

@Preview
@Composable
fun ButtonPanelPreview() {

    val item1 = ButtonPanelItem(
        title = "title1",
        image = Icons.Default.Add,
        onClick = { }
    )

    val item2 = ButtonPanelItem(
        title = "title2",
        image = Icons.Default.List,
        onClick = { }
    )

    val item3 = ButtonPanelItem(
        title = "title3",
        image = Icons.Default.AccountBox,
        onClick = { }
    )
    RonSwansonQuotesTheme {
        ButtonPanelComposable(
            items = listOf(item1, item2, item3)
        )
    }
}

@Preview
@Composable
fun ButtonPanelDarkThemePreview() {

    val item1 = ButtonPanelItem(
        title = "title1",
        image = Icons.Default.Add,
        onClick = { }
    )

    val item2 = ButtonPanelItem(
        title = "title2",
        image = Icons.Default.List,
        onClick = { }
    )

    val item3 = ButtonPanelItem(
        title = "title3",
        image = Icons.Default.AccountBox,
        onClick = { }
    )
    RonSwansonQuotesTheme(
        darkTheme = true
    ) {
        ButtonPanelComposable(
            items = listOf(item1, item2, item3)
        )
    }
}