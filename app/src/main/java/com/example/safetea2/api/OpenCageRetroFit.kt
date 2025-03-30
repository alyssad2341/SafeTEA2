package com.example.safetea2.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenCageRetrofit {

    private const val BASE_URL = "https://api.opencagedata.com/"

    val api: OpenCageApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenCageApi::class.java)
    }
}
