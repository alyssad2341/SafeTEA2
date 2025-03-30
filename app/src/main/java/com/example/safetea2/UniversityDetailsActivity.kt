package com.example.safetea2

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safetea2.api.NewsRetrofitInstance
import com.example.safetea2.api.OpenCageResponse
import com.example.safetea2.api.OpenCageRetrofit
import com.example.safetea2.model.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.CheckBox
import com.google.android.material.bottomnavigation.BottomNavigationView

class UniversityDetailsActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var newsAdapter: NewsListAdapter

    private val NEWS_API_KEY = "0c0f18806c974e70a3b75689291461fe"
    private val OPENCAGE_API_KEY = "15b9a496e88a48e69906d2aa0542e5ce"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_universitydetails)

        val universityName = intent.getStringExtra("UNIVERSITY_NAME") ?: "Unknown University"

        val universityNameTextView = findViewById<TextView>(R.id.universityNameTextView)
        val universityCityTextView = findViewById<TextView>(R.id.universityCityTextView)

        val saveCheckBox = findViewById<CheckBox>(R.id.saveCheckBox)
        newsRecyclerView = findViewById(R.id.newsRecyclerView)

        universityNameTextView.text = universityName

        sharedPreferences = getSharedPreferences("SavedUniversities", MODE_PRIVATE)

        saveCheckBox.isChecked = getSavedUniversities().contains(universityName)

        saveCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                saveUniversity(universityName)
                Toast.makeText(this, "University saved successfully", Toast.LENGTH_SHORT).show()
            } else {
                removeUniversity(universityName)
                Toast.makeText(this, "University removed from saved list", Toast.LENGTH_SHORT).show()
            }
        }

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnItemSelectedListener { item ->
            val handled = when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.navigation_dashboard -> {
                    startActivity(Intent(this, SavedToDashboard::class.java))
                    true
                }
                else -> false
            }
            handled
        }

        setupRecyclerView()
        fetchNewsArticles(universityName)
        fetchCityofUniversity(universityName, universityCityTextView)
    }

    private fun saveUniversity(universityName: String) {
        val savedList = getSavedUniversities().toMutableList()

        if (!savedList.contains(universityName)) {
            savedList.add(universityName)
            val editor = sharedPreferences.edit()
            val json = Gson().toJson(savedList)
            editor.putString("SAVED_UNIVERSITIES", json)
            editor.apply()
        }
    }
    private fun removeUniversity(universityName: String) {
        val savedList = getSavedUniversities().toMutableList()

        if (savedList.contains(universityName)) {
            savedList.remove(universityName)
            val editor = sharedPreferences.edit()
            val json = Gson().toJson(savedList)
            editor.putString("SAVED_UNIVERSITIES", json)
            editor.apply()
        }
    }

    private fun getSavedUniversities(): List<String> {
        val json = sharedPreferences.getString("SAVED_UNIVERSITIES", "[]")
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, type) ?: emptyList()
    }

    private fun setupRecyclerView() {
        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        newsAdapter = NewsListAdapter(emptyList())
        newsRecyclerView.adapter = newsAdapter
    }

    private fun fetchNewsArticles(universityName: String) {

        val query = "\"$universityName\""
        val call = NewsRetrofitInstance.api.getUniversityNews(query, NEWS_API_KEY)

        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    response.body()?.articles?.let { articles ->
                        val filteredArticles = articles.filter { it.title.contains(universityName, ignoreCase = true) }
                        newsAdapter.updateArticles(filteredArticles)
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Toast.makeText(this@UniversityDetailsActivity, "Failed to fetch news", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun fetchCityofUniversity(universityName: String, cityTextView: TextView) {
        val call = OpenCageRetrofit.api.getCityFromUniversity(universityName, OPENCAGE_API_KEY)
        call.enqueue(object : Callback<OpenCageResponse> {
            override fun onResponse(call: Call<OpenCageResponse>, response: Response<OpenCageResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()?.results?.get(0)?.components
                    val city = result?.city
                    val stateCode = result?.state_code

                    if (city != null && stateCode != null) {
                        cityTextView.text = "City: $city, State: $stateCode"
                        fetchCrimeData(stateCode)
                    } else {
                        cityTextView.text = "City/State not found"
                    }
                } else {
                    cityTextView.text = "Error fetching city/state"
                }
            }
            override fun onFailure(call: Call<OpenCageResponse>, t: Throwable) {
                cityTextView.text = "Failed to fetch city"
            }
        })
    }
    private fun fetchCrimeData(stateCode: String) {
        val crimeTextView = findViewById<TextView>(R.id.crimeStatsTextView)
        crimeTextView.text = "Violent Crime Index: 68 \n " +
                "Physical Harm Score: 72 \n" +
                "Health and Medical Safety Score: 50 \n"+
                "Women’s Safety Score: 88 \n"+
                "LGBTQ+ Safety Score: 66 \n"+
                "Theft Score: 77 \n"+
                "Political Freedoms Score: 33 \n"+
                "*** Scores are not based on actual data. Would have used GeoSure API to generate data. ***"
//        try {
//            val call = CrimeometerRetrofit.api.getCrimeData(stateCode, "2025", CRIMEOMETER_API_KEY)
//            call.enqueue(object : Callback<CrimeometerResponse> {
//                override fun onResponse(call: Call<CrimeometerResponse>, response: Response<CrimeometerResponse>) {
//
//                    if (response.isSuccessful) {
//                        val crimeStats = response.body()
//                        crimeStats?.let {
//                            val violentCrime = it.data[0].violentCrime
//                            val crimeTextView = findViewById<TextView>(R.id.crimeStatsTextView)
//                            crimeTextView.text = "Violent Crime Index for $stateCode: $violentCrime"
//                        }
//                    } else {
//                        Log.e("fetchCrimeData", "Error Response: ${response.errorBody()?.string()}")
//                    }
//                }
//                override fun onFailure(call: Call<CrimeometerResponse>, t: Throwable) {
//                }
//            })
//        } catch (e: Exception) {
//            Log.e("fetchCrimeData", "Error making API call: ${e.message}")
//            e.printStackTrace()
//        }
    }


}
