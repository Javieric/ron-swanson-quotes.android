package com.javieric.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javieric.composables.theme.RonSwansonQuotesTheme

@Composable
fun ErrorMessageComposable(
    modifier: Modifier = Modifier,
    message: String
) {

    Column(
        modifier = modifier,
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .testTag("quoteText"),
            text = message,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorMessageComposablePreview() {
    RonSwansonQuotesTheme {
        ErrorMessageComposable(message = "error")
    }
}