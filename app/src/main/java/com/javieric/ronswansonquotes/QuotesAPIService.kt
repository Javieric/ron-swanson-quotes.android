package com.javieric.ronswansonquotes

import javax.inject.Inject

interface IQuotesAPIService {

    suspend fun requestNewQuote(): String
}

class QuotesAPIService @Inject constructor(
    private val api: IQuotesAPI,
) : IQuotesAPIService {

    override suspend fun requestNewQuote(): String {

        return runCatching {
            api.requestNewQuote()
        }.onSuccess { response ->
            check(response.isSuccessful) {"request was not successful"}
        }.mapCatching { response ->
            response.body()?.get(0) ?: ""
        }.getOrThrow()
    }
}
