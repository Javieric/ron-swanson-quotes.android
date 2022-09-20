package com.javieric.ronswansonquotes

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javieric.ronswansonquotes.theme.RonSwansonQuotesTheme

@Composable
fun ButtonComposable(
    image: ImageVector,
    onClick: () -> Unit,
    title: String,
) {

    IconButton(
        modifier = Modifier.testTag("iconButton_$title").padding(20.dp),
        onClick = { onClick() },
    ) {
        Icon(
            modifier = Modifier.size(80.dp, 80.dp).testTag("icon_$title"),
            imageVector = image,
            contentDescription = title,
            tint = MaterialTheme.colors.primary,
        )
    }
}

@Preview
@Composable
fun ButtonPreview() {
    RonSwansonQuotesTheme {
        ButtonComposable(
            image = Icons.Default.Add,
            onClick = { },
            title = "add",
        )
    }
}

@Preview
@Composable
fun ButtonDarkThemePreview() {
    RonSwansonQuotesTheme(
        darkTheme = true,
    ) {
        ButtonComposable(
            image = Icons.Default.Add,
            onClick = { },
            title = "add",
        )
    }
}