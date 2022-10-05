package com.javieric.ronswansonquotes

import com.javieric.ronswansonquotes.di.DaggerApplicationComponent
import javax.inject.Inject

class QuotesUseCase @Inject constructor(
    private val quotesAPIService: IQuotesAPIService,
): IQuotesUseCase {

    init {
        DaggerApplicationComponent.builder().build().inject(this)
    }

    override suspend fun requestQuote(): String {
        return quotesAPIService.requestNewQuote()
    }
}