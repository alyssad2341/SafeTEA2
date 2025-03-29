package com.example.safetea2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UniversityDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_universitydetails)

        val universityName = intent.getStringExtra("UNIVERSITY_NAME") ?: "Unknown University"

        val universityNameTextView = findViewById<TextView>(R.id.universityNameTextView)
        universityNameTextView.text = universityName
    }
}
