package com.javieric.ronswansonquotes.di

import com.javieric.ronswansonquotes.MainActivity
import com.javieric.ronswansonquotes.MainViewModel
import com.javieric.ronswansonquotes.RequestQuoteUseCase
import dagger.Component

@Component(
    modules = [ApplicationModule::class]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)
    fun inject(activity: MainViewModel)
    fun inject(requestQuoteUseCase: RequestQuoteUseCase)
}