package com.example.safetea2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView

class SearchResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchresults)

        val searchQuery = intent.getStringExtra("SEARCH_QUERY") ?: "No query"

        val resultsTextView = findViewById<TextView>(R.id.resultsTextView)
        resultsTextView.text = "Results for: $searchQuery"

        val homeButton = findViewById<Button>(R.id.backbutton)

        homeButton.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
        }
    }
}