package com.lesterderequito.masterdetailapplication.common

import android.content.Context
import android.net.ConnectivityManager

@Suppress("DEPRECATION")
class NetworkSettings {

    companion object {
        fun isConnectionAvailable(context: Context): Boolean {
            val cm = context.applicationContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val net = cm.activeNetworkInfo
            return net != null && (net.type == ConnectivityManager.TYPE_MOBILE || net.type == ConnectivityManager.TYPE_WIFI)
        }
    }
}