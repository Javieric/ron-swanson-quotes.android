package com.javieric.ronswansonquotes

import android.app.Application
import com.javieric.ronswansonquotes.di.DaggerApplicationComponent

class MyApplication: Application() {

    val appComponent = DaggerApplicationComponent.create()
}