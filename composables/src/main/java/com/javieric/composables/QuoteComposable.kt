package com.javieric.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javieric.composables.theme.RonSwansonQuotesTheme

@Composable
fun QuoteComposable(
    modifier: Modifier = Modifier,
    quote: String
) {

    Column(
        modifier = modifier,
    ) {
        Image(
            modifier = Modifier
                .height(50.dp)
                .align(alignment = Alignment.Start)
                .padding(10.dp)
                .testTag("openQuotesImage"),
            painter = painterResource(id = R.drawable.ic_quotation_marks_1),
            contentDescription = "open quotes",
            colorFilter = ColorFilter.tint(color = MaterialTheme.colors.primary),
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .testTag("quoteText"),
            text = quote,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.primary,
        )

        Image(
            modifier = Modifier
                .height(50.dp)
                .align(alignment = Alignment.End)
                .padding(10.dp)
                .testTag("closeQuotesImage"),
            painter = painterResource(id = R.drawable.ic_quotation_marks_2),
            contentDescription = "close quotes",
            colorFilter = ColorFilter.tint(color = MaterialTheme.colors.primary),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuoteComposablePreview() {
    RonSwansonQuotesTheme {
        QuoteComposable(quote = "new quote")
    }
}