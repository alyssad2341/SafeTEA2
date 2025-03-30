package com.example.safetea2

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safetea2.api.RetrofitInstance
import com.example.safetea2.model.University
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent
import android.widget.Button
import android.content.Context
import android.content.SharedPreferences
import com.example.safetea2.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SavedToDashboard : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var universityAdapter: UniversityListAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_universities)

        sharedPreferences = getSharedPreferences("SavedUniversities", MODE_PRIVATE)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val savedUniversities = getSavedUniversities()

        universityAdapter = UniversityListAdapter(savedUniversities.map { University(it) })
        recyclerView.adapter = universityAdapter

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnItemSelectedListener { item ->
            val handled = when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.navigation_dashboard -> {
                    // Redirect to SavedUniversitiesActivity when Dashboard is clicked
                    true
                }
                else -> false
            }
            handled
        }

    }


    private fun getSavedUniversities(): List<String> {
        val json = sharedPreferences.getString("SAVED_UNIVERSITIES", "[]")
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, type) ?: emptyList()
    }
}