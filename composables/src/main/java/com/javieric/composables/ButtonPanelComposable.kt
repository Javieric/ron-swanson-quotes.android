package com.javieric.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
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
                image = ImageVector.vectorResource(id = it.image),
                onClick = it.onClick,
                title = it.title,
            )
        }
    }
}

data class ButtonPanelItem(
    val title: String,
    val image: Int,
    val onClick: () -> Unit,
)

@Preview
@Composable
fun ButtonPanelPreview() {

    val item1 = ButtonPanelItem(
        title = "title1",
        image = androidx.appcompat.R.drawable.abc_ic_menu_share_mtrl_alpha
    ) { }

    val item2 = ButtonPanelItem(
        title = "title2",
        image = androidx.appcompat.R.drawable.abc_ic_menu_cut_mtrl_alpha
    ) { }

    val item3 = ButtonPanelItem(
        title = "title3",
        image = androidx.appcompat.R.drawable.abc_ic_menu_copy_mtrl_am_alpha
    ) { }
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
        image = androidx.appcompat.R.drawable.abc_ic_menu_share_mtrl_alpha
    ) { }

    val item2 = ButtonPanelItem(
        title = "title2",
        image = androidx.appcompat.R.drawable.abc_ic_menu_cut_mtrl_alpha
    ) { }

    val item3 = ButtonPanelItem(
        title = "title3",
        image = androidx.appcompat.R.drawable.abc_ic_menu_copy_mtrl_am_alpha
    ) { }
    RonSwansonQuotesTheme(
        darkTheme = true
    ) {
        ButtonPanelComposable(
            items = listOf(item1, item2, item3)
        )
    }
}