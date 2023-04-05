package com.javieric.ronswansonquotes.di

import com.javieric.ronswansonquotes.*
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApplicationModule {

    @Provides
    fun providesQuotesUseCase(quotesAPIService: IQuotesAPIService): IRequestQuoteUseCase {
        return RequestQuoteUseCase(quotesAPIService)
    }

    @Provides
    fun providesQuotesAPIService(api: IQuotesAPI): IQuotesAPIService {
        return QuotesAPIService(api)
    }

    @Provides
    fun provideRetrofitService(): IQuotesAPI {
        // Whenever Dagger needs to provide an instance of type LoginRetrofitService,
        // this code (the one inside the @Provides method) is run.
        return Retrofit.Builder()
            .baseUrl("http://ron-swanson-quotes.herokuapp.com/v2/quotes/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IQuotesAPI::class.java)
    }
}