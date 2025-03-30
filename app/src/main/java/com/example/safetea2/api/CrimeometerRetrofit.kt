package com.example.safetea2.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CrimeometerRetrofit {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.usa.gov/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: CrimeometerAPI = retrofit.create(CrimeometerAPI::class.java)
}
