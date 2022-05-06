package com.lesterderequito.masterdetailapplication.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Boolean.FALSE
import java.util.concurrent.TimeUnit

class RetroInstance {

    companion object {
        private const val baseURL = "https://picsum.photos/"
        private val retrofit: Retrofit? = null

        fun getRetroInstance() : Retrofit {
            if (retrofit == null) {
                return Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build()
            }
            return retrofit;
        }
    }
}

private fun getOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .followRedirects(FALSE)
        .followSslRedirects(FALSE)
        .connectTimeout(5, TimeUnit.MINUTES)
        .readTimeout(5, TimeUnit.MINUTES)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()
}