package com.example.safetea2

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.safetea2.databinding.ActivityMainBinding
import android.widget.Button
import android.content.Intent
import com.example.safetea2.ui.dashboard.DashboardFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navView.setOnItemSelectedListener { item ->
            val handled = when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.navigation_dashboard -> {
                    // Redirect to SavedUniversitiesActivity when Dashboard is clicked
                    startActivity(Intent(this, SavedToDashboard::class.java))
                    true
                }
                else -> false
            }
            handled
        }

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
        )



        // Search Button Functionality (Moved Outside setOnItemSelectedListener)
        val searchView = findViewById<SearchView>(R.id.searchView)
        val searchButton = findViewById<Button>(R.id.spillbutton)

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