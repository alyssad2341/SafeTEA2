package com.example.safetea2

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.widget.Toast
import android.content.Intent

class UniversityDetailsActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_universitydetails)

        val universityName = intent.getStringExtra("UNIVERSITY_NAME") ?: "Unknown University"

        val universityNameTextView = findViewById<TextView>(R.id.universityNameTextView)
        val saveButton = findViewById<Button>(R.id.saveButton)

        universityNameTextView.text = universityName

        sharedPreferences = getSharedPreferences("SavedUniversities", MODE_PRIVATE)

        saveButton.setOnClickListener {
            saveUniversity(universityName)
            Toast.makeText(this, "University saved successfully", Toast.LENGTH_SHORT).show()
        }

        val homeButton2 = findViewById<Button>(R.id.backbutton2)

        homeButton2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
    //updated

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

    private fun getSavedUniversities(): List<String> {
        val json = sharedPreferences.getString("SAVED_UNIVERSITIES", "[]")
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, type) ?: emptyList()
    }

}
