package com.javieric.ronswansonquotes

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

        lifecycleScope.launchWhenCreated {
            viewModel.quoteState.collect {
                quoteState = it

                setContent {
                    RonSwansonQuotesTheme {
                        QuotesUI(quoteState) { viewModel.requestQuote() }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun QuotesUI(quoteState: QuoteState?,
                     onClick: ()->Unit
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

            Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {

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

            Button(
                modifier = Modifier
                    .defaultMinSize(
                        minHeight = 32.dp,
                        minWidth = 104.dp,
                    )
                    .clickable { onClick() },
                onClick = {
                    onClick()
                },
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.Red
                )
            ) {
                Text("New Quote")
            }
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

@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    RonSwansonQuotesTheme {
        QuotesUI(quoteState = QuoteState.Loading) { null }
    }
}

@Preview(showBackground = true)
@Composable
fun SuccessPreview() {
    RonSwansonQuotesTheme {
        QuotesUI(quoteState = QuoteState.Success(quote = "new quote")) { null }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorPreview() {
    RonSwansonQuotesTheme {
        QuotesUI(quoteState = QuoteState.Error(message = "error message")) { null }
    }
}