package com.javieric.ronswansonquotes

import retrofit2.Response
import retrofit2.http.GET

interface IQuotesAPI {

    @GET("http://ron-swanson-quotes.herokuapp.com/v2/quotes/")
    suspend fun requestNewQuote(): Response<List<String>>
}