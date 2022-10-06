package com.javieric.ronswansonquotes

sealed class QuoteState {

    data class Success(val quote: String) : QuoteState()
    object Loading : QuoteState()
    open class Error(val message: String? = null) : QuoteState()
    object ConnectionError : Error()
}