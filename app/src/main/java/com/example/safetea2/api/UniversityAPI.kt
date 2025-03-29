package com.example.safetea2.api

import com.example.safetea2.model.University
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UniversityApiService {
    @GET("search")
    fun getUniversities(@Query("name") location: String): Call<List<University>>
}


