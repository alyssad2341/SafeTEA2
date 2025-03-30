package com.example.safetea2.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenCageApi {

    @GET("geocode/v1/json")
    fun getCityFromUniversity(
        @Query("q") universityName: String,
        @Query("key") apiKey: String
    ): Call<OpenCageResponse>
}