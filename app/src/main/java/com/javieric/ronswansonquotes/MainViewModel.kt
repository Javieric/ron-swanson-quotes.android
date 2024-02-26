package com.javieric.ronswansonquotes

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.javieric.ronswansonquotes.di.DaggerApplicationComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainViewModel (
    application: Application
): AndroidViewModel(application) {

    @Inject
    lateinit var quotesUseCase: IRequestQuoteUseCase

    private var _quoteState = MutableStateFlow<QuoteState>(QuoteState.Loading)
    val quoteState: StateFlow<QuoteState> = _quoteState

    var clipboardLiveData: MutableLiveData<Boolean?> = MutableLiveData()

    init {

        Timber.d("init()")
        DaggerApplicationComponent.builder().build().inject(this)
        requestQuote()
    }

    fun requestQuote() {

        Timber.d("requestQuote()")
        if (NetworkUtils.checkInternetConnection(getApplication())) {

            _quoteState.value = QuoteState.Loading
            viewModelScope.launch(Dispatchers.IO) {

                _quoteState.value = try {

                    Timber.d("requestQuote() - requesting quote")
                    QuoteState.Success(quotesUseCase.requestQuote())
                } catch (e: Exception) {

                    Timber.d("requestQuote() - error: ${e.message}")
                    QuoteState.Error("error: ${e.message}")
                }
            }
        } else {

            Timber.d("requestQuote() - connection error")
            _quoteState.value = QuoteState.ConnectionError
        }
    }

    fun copyToClipboard(context: Context) {

        Timber.d("copyToClipboard()")
        if (quoteState.value is QuoteState.Success) {

            (quoteState.value as QuoteState.Success).quote.let { quote ->

                Timber.d("copyToClipboard() - quote: $quote")
                val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("text", "\"$quote\" - Ron Swanson")

                clipData?.let {
                    clipboardManager.setPrimaryClip(it)
                }

                clipboardLiveData.value = true
                Timber.d("copyToClipboard() - quote copied to clipboard")
            }
        }
    }
}
