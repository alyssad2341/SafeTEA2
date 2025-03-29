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

class SearchResultsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var universityAdapter: UniversityListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchresults)

        val searchQuery = intent.getStringExtra("SEARCH_QUERY") ?: "No query"

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        universityAdapter = UniversityListAdapter(emptyList())
        recyclerView.adapter = universityAdapter

        if(searchQuery.isNotEmpty()){
            fetchUniversities(searchQuery)
        }


        val resultsTextView = findViewById<TextView>(R.id.resultsTextView)
        resultsTextView.text = "Results for: $searchQuery"

        val homeButton = findViewById<Button>(R.id.backbutton)

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchUniversities(query: String) {
        RetrofitInstance.api.getUniversities(query).enqueue(object : Callback<List<University>> {
            override fun onResponse(call: Call<List<University>>, response: Response<List<University>>) {
                if (response.isSuccessful) {
                    val universities = response.body() ?: emptyList()
                    universityAdapter.updateData(universities)
                } else {
                    Toast.makeText(applicationContext, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<University>>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                Log.e("SearchResultsActivity", "API call failed", t)
            }
        })
    }

}
