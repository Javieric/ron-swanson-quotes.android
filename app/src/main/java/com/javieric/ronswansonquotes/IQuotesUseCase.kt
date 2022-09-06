package com.javieric.ronswansonquotes

interface IQuotesUseCase {

    suspend fun requestQuote(): String

}
