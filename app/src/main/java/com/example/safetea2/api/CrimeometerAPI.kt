package com.example.safetea2.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CrimeometerAPI {
    @GET("path/to/fbi/crime/data/{state_code}")
    fun getCrimeData(
        @Path("state_code") stateCode: String,
        @Query("year") year: String,
        @Query("api_key") apiKey: String
    ): Call<CrimeometerResponse>
}