package com.javieric.ronswansonquotes

interface IQuotesAPIService {

    suspend fun requestNewQuote(): String
}