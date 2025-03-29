package com.example.safetea2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.safetea2.api.RetrofitInstance
import com.example.safetea2.model.University
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private val _universities = MutableLiveData<List<University>>()
    val universities: LiveData<List<University>> get() = _universities

    fun searchUniversities(location: String) {
        RetrofitInstance.api.getUniversities(location).enqueue(object : Callback<List<University>> {
            override fun onResponse(call: Call<List<University>>, response: Response<List<University>>) {
                if (response.isSuccessful) {
                    _universities.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<University>>, t: Throwable) {
                _universities.value = emptyList() // Handle error case
            }
        })
    }
}
