package com.example.safetea2.api

import com.example.safetea2.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("everything")
    fun getUniversityNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String,
        @Query("pageSize") pageSize: Int = 100,
        @Query("title") titleFilter: String = ""
    ): Call<NewsResponse>
}