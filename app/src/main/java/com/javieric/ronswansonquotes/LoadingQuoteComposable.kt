package com.javieric.ronswansonquotes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.javieric.ronswansonquotes.theme.RonSwansonQuotesTheme

@Composable
fun LoadingQuoteComposable() {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(Modifier.testTag("loadingIndicator"))
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingQuoteComposablePreview() {
    RonSwansonQuotesTheme {
        LoadingQuoteComposable()
    }
}
