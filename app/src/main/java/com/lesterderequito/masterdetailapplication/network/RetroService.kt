package com.lesterderequito.masterdetailapplication.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroService {

    @GET("v2/list")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20): Response<List<PhotoInfo>>
}