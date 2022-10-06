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
import javax.inject.Inject


class MainViewModel (
    application: Application
): AndroidViewModel(application) {

    @Inject
    lateinit var quotesUseCase: IQuotesUseCase

    private var _quoteState = MutableStateFlow<QuoteState>(QuoteState.Loading)
    val quoteState: StateFlow<QuoteState> = _quoteState

    var clipboardLiveData: MutableLiveData<Boolean?> = MutableLiveData()

    init {

        DaggerApplicationComponent.builder().build().inject(this)

        requestQuote()
    }

    fun requestQuote() {

        if (NetworkUtils.checkInternetConnection(getApplication())) {

            viewModelScope.launch(Dispatchers.IO) {

                _quoteState.value = QuoteState.Loading
                _quoteState.value = try {
//                delay(1000)
                    QuoteState.Success(quotesUseCase.requestQuote())
                } catch (e: Exception) {
                    QuoteState.Error("error: ${e.message}")
                }
            }
        } else {
            _quoteState.value = QuoteState.ConnectionError
        }
    }

    fun copyToClipboard(context: Context) {

        if (quoteState.value is QuoteState.Success) {

            (quoteState.value as? QuoteState.Success)?.quote?.let {

                val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("text", "\"$it\" - Ron Swanson")

                clipData?.let {
                    clipboardManager.setPrimaryClip(it)
                }

                clipboardLiveData.value = true
            }
        }
    }
}