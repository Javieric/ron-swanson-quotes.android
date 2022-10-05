package com.javieric.ronswansonquotes

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {

    fun checkInternetConnection(context: Context): Boolean {

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isAvailable && cm.activeNetworkInfo!!
            .isConnected
    }
}