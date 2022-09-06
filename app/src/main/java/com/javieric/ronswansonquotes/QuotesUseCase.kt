package com.javieric.ronswansonquotes

import javax.inject.Inject

class QuotesUseCase @Inject constructor(
    private val quotesAPIService: IQuotesAPIService,
): IQuotesUseCase {

    override suspend fun requestQuote(): String {
        return quotesAPIService.requestNewQuote()
    }
}