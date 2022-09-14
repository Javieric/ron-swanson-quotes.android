package com.javieric.ronswansonquotes

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.javieric.ronswansonquotes.di.DaggerApplicationComponent
import com.javieric.ronswansonquotes.theme.RonSwansonQuotesTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    @Inject
    lateinit var viewModel: MainViewModel
//    private val viewModel by viewModels<MainViewModel>()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {

        DaggerApplicationComponent.builder().build().inject(this)

        super.onCreate(savedInstanceState)

        var quoteState: QuoteState?

        viewModel.clipboardLiveData.observe(this) {
            onQuoteCopiedToClipboard()
        }

        val newQuoteMenuIcon = ButtonPanelItem(
            title = "New Quote",
            image = Icons.Default.Refresh,
            onClick = { viewModel.requestQuote() }
        )

        val copyMenuIcon = ButtonPanelItem(
            title = "Copy",
            image = Icons.Default.Edit,
            onClick = { viewModel.copyToClipboard(this) }
        )

        val shareMenuIcon = ButtonPanelItem(
            title = "Share",
            image = Icons.Default.Share,
            onClick = {  }
        )

        val favouriteMenuIcon = ButtonPanelItem(
            title = "Favourite",
            image = Icons.Default.Favorite,
            onClick = {  }
        )

        val menuIconList = listOf(newQuoteMenuIcon, copyMenuIcon, shareMenuIcon, favouriteMenuIcon)

        lifecycleScope.launchWhenCreated {
            viewModel.quoteState.collect {
                quoteState = it

                setContent {
                    RonSwansonQuotesTheme {
                        QuotesUI(
                            quoteState = quoteState,
                            menuIconList = menuIconList
                        )
                    }
                }
            }
        }
    }

    private fun onQuoteCopiedToClipboard() {

        Toast.makeText(this, getString(R.string.copied_to_clipboard), Toast.LENGTH_LONG).show()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun QuotesUI(
    quoteState: QuoteState?,
    menuIconList: List<ButtonPanelItem> = emptyList()
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Image(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .padding(48.dp),
                    painter = painterResource(id = R.drawable.ic_ron_swanson),
                contentDescription = "logo",
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.primary),
            )

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)) {

                AnimatedContent(
                    targetState = quoteState,
                    transitionSpec = {

                        if (targetState is QuoteState.Loading) {
                            loadingAnimation()
                        } else {
                            newQuoteAnimation()
                        }
                    }
                ) { targetState ->

                    when (targetState) {
                        is QuoteState.Success -> QuoteComposable(modifier = Modifier.fillMaxWidth(), quote = targetState.quote)
                        is QuoteState.Loading -> LoadingQuoteComposable()
                        is QuoteState.Error -> ErrorMessageComposable(modifier = Modifier.fillMaxWidth(), message = targetState.message)
                    }
                }
            }

            ButtonPanelComposable(
                modifier = Modifier.fillMaxWidth(),
                items = menuIconList
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun newQuoteAnimation() = slideInHorizontally(
    animationSpec = tween(durationMillis = 200, easing = LinearEasing),
    initialOffsetX = { fullWidth -> fullWidth }
) with
        fadeOut(animationSpec = tween(durationMillis = 200, easing = LinearEasing))

@OptIn(ExperimentalAnimationApi::class)
private fun loadingAnimation() = fadeIn(
    animationSpec = tween(durationMillis = 200, easing = LinearEasing)
) with
        slideOutHorizontally(
            animationSpec = tween(durationMillis = 200, easing = LinearEasing),
            targetOffsetX = { fullWidth -> -fullWidth }
        )

@Preview
@Composable
fun LoadingPreview() {
    RonSwansonQuotesTheme {
        QuotesUI(
            quoteState = QuoteState.Loading,
            menuIconList = generateMenuIcons(),
        )
    }
}

@Preview
@Composable
fun SuccessPreview() {
    RonSwansonQuotesTheme {
        QuotesUI(
            quoteState = QuoteState.Success(quote = "new quote"),
            menuIconList = generateMenuIcons(),
        )
    }
}

@Preview
@Composable
fun ErrorPreview() {
    RonSwansonQuotesTheme {
        QuotesUI(
            quoteState = QuoteState.Error(message = "error message"),
            menuIconList = generateMenuIcons(),
        )
    }
}

@Preview
@Composable
fun LoadingDarkThemePreview() {
    RonSwansonQuotesTheme(
        darkTheme = true
    ) {
        QuotesUI(
            quoteState = QuoteState.Loading,
            menuIconList = generateMenuIcons(),
        )
    }
}

@Preview
@Composable
fun SuccessDarkThemePreview() {
    RonSwansonQuotesTheme(
        darkTheme = true
    ) {
        QuotesUI(
            quoteState = QuoteState.Success(quote = "new quote"),
            menuIconList = generateMenuIcons(),
        )
    }
}

@Preview
@Composable
fun ErrorDarkThemePreview() {
    RonSwansonQuotesTheme(
        darkTheme = true
    ) {
        QuotesUI(
            quoteState = QuoteState.Error(message = "error message"),
            menuIconList = generateMenuIcons(),
        )
    }
}

private fun generateMenuIcons(): List<ButtonPanelItem> {
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

    val favouriteMenuIcon = ButtonPanelItem(
        title = "Favourite",
        image = Icons.Default.Favorite,
        onClick = {  }
    )

    return listOf(newQuoteMenuIcon, copyMenuIcon, shareMenuIcon, favouriteMenuIcon)
}