package com.lesterderequito.masterdetailapplication.network

import com.google.gson.annotations.SerializedName

data class PhotoInfo (val id: Int,
                      val author: String,
                      val width: Int,
                      val height: Int,
                      val url: String,
                      @SerializedName("download_url")
                      val downloadUrl: String)