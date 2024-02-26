package com.javieric.ronswansonquotes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.javieric.composables.*
import com.javieric.composables.theme.RonSwansonQuotesTheme
import com.javieric.ronswansonquotes.di.DaggerApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

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
            image = R.drawable.next_right_arrow_svgrepo_com
        ) { viewModel.requestQuote() }

        val copyMenuIcon = ButtonPanelItem(
            title = "Copy",
            image = R.drawable.copy_svgrepo_com
        ) { viewModel.copyToClipboard(this) }

        val shareMenuIcon = ButtonPanelItem(
            title = "Share",
            image = R.drawable.share_1100_svgrepo_com
        ) { share() }

//        val favouriteMenuIcon = ButtonPanelItem(
//            title = "Favourite",
//            image = Icons.Default.Favorite,
//            onClick = {  }
//        )

        val menuIconList = listOf(newQuoteMenuIcon, copyMenuIcon, shareMenuIcon, /*favouriteMenuIcon*/)

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

    private fun share() {

        if (viewModel.quoteState.value is QuoteState.Success) {

            (viewModel.quoteState.value as? QuoteState.Success)?.quote?.let {

                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "\"$it\" - Ron Swanson")
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
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
                        is QuoteState.ConnectionError -> {

                            ErrorMessageComposable(
                                modifier = Modifier.fillMaxWidth(),
                                message = stringResource(id = R.string.connection_error)
                            )
                        }
                        is QuoteState.Error -> {

                            ErrorMessageComposable(
                                modifier = Modifier.fillMaxWidth(),
                                message = targetState.message ?: stringResource(id = R.string.unknown_error)
                            )
                        }
                        else -> {}
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
        image = R.drawable.next_right_arrow_svgrepo_com
    ) { }

    val copyMenuIcon = ButtonPanelItem(
        title = "Copy",
        image = R.drawable.copy_svgrepo_com
    ) { }

    val shareMenuIcon = ButtonPanelItem(
        title = "Share",
        image = R.drawable.share_1100_svgrepo_com
    ) { }

//    val favouriteMenuIcon = ButtonPanelItem(
//        title = "Favourite",
//        image = Icons.Default.Favorite
//    ) { }

    return listOf(newQuoteMenuIcon, copyMenuIcon, shareMenuIcon/*, favouriteMenuIcon*/)
}