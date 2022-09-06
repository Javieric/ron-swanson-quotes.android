package com.javieric.ronswansonquotes.di

import com.javieric.ronswansonquotes.MainActivity
import com.javieric.ronswansonquotes.MainViewModel
import dagger.Component

@Component(
    modules = [ApplicationModule::class]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)
    fun inject(activity: MainViewModel)
}