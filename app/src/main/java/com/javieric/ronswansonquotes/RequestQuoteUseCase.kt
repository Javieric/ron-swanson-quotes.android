package com.javieric.ronswansonquotes

import com.javieric.ronswansonquotes.di.DaggerApplicationComponent
import javax.inject.Inject

interface IRequestQuoteUseCase {

    suspend fun requestQuote(): String
}

class RequestQuoteUseCase @Inject constructor(
    private val quotesAPIService: IQuotesAPIService,
): IRequestQuoteUseCase {

    init {
        DaggerApplicationComponent.builder().build().inject(this)
    }

    override suspend fun requestQuote(): String {
        return quotesAPIService.requestNewQuote()
    }
}
