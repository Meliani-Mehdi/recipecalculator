package com.example.recipecalculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class MainNavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_navigation)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val navbar = findViewById<ChipNavigationBar>(R.id.bottom_nav_bar)

        if (savedInstanceState == null) {
            navbar.setItemSelected(R.id.nav_ingredients, true)
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, IngredientsFragment())
                .commit()
        }

        navbar.setOnItemSelectedListener { id ->
            val selectedFragment: Fragment = when (id) {
                R.id.nav_ingredients -> IngredientsFragment()
                R.id.nav_recipes -> RecipesFragment()
                else -> IngredientsFragment()
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, selectedFragment)
                .commit()
        }
    }
}