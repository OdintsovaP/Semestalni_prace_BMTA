package com.example.mnauhouse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)  // Nastavení layout pro aktivitu

        // Najít NavHostFragment (kontejner pro fragmenty)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController  // Kontrolér pro navigaci

        // Najít BottomNavigationView (dolní navigační panel)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Propojit BottomNavigationView s navController (automatická navigace)
        bottomNavigationView.setupWithNavController(navController)
    }
}