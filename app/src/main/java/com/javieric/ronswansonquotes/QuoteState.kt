package com.javieric.ronswansonquotes

sealed class QuoteState {

    data class Success(val quote: String) : QuoteState()
    data class Error(val message: String) : QuoteState()
    object Loading : QuoteState()
}