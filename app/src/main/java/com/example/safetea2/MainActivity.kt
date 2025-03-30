package com.example.safetea2

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.safetea2.databinding.ActivityMainBinding
import android.widget.Button
import android.content.Intent


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnItemSelectedListener { item ->
            val handled = when (item.itemId) {
                R.id.navigation_home -> {
                    //Go to MainActivity when home is selected
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.navigation_dashboard -> {
                    //Go to SavedToDashboard when dashboard is selected
                    startActivity(Intent(this, SavedToDashboard::class.java))
                    true
                }
                else -> false
            }
            handled
        }

        val searchView = findViewById<SearchView>(R.id.searchView)
        val searchButton = findViewById<Button>(R.id.spillbutton)

        //Go to SearchResultsActivity when search button is clicked
        searchButton.setOnClickListener {
            val query = searchView.query.toString().trim()
            if (query.isNotEmpty()) {
                val intent = Intent(this, SearchResultsActivity::class.java)
                intent.putExtra("SEARCH_QUERY", query)
                startActivity(intent)
            }
        }
    }
}