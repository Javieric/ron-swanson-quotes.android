package com.javieric.ronswansonquotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javieric.ronswansonquotes.di.DaggerApplicationComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MainViewModel (
    private val quotesUseCase: IQuotesUseCase,
): ViewModel() {

    private var _quoteState = MutableStateFlow<QuoteState>(QuoteState.Loading)
    val quoteState: StateFlow<QuoteState> = _quoteState

    init {

        DaggerApplicationComponent.builder().build().inject(this)

        requestQuote()
    }

    fun requestQuote() {
        viewModelScope.launch(Dispatchers.IO) {

            _quoteState.value = QuoteState.Loading
            _quoteState.value = try {
//                delay(1000)
                QuoteState.Success(quotesUseCase.requestQuote())
            } catch (e: Exception) {
                QuoteState.Error("error: ${e.message}")
            }
        }
    }
}