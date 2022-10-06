package com.javieric.ronswansonquotes

import android.app.Application
import com.javieric.ronswansonquotes.di.DaggerApplicationComponent
import timber.log.Timber
import timber.log.Timber.*


class MyApplication: Application() {

    val appComponent = DaggerApplicationComponent.create()

    override fun onCreate() {

        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}