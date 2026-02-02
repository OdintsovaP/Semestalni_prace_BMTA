package com.example.mnauhouse

import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mnauhouse.data.repository.UserRepository
import com.example.mnauhouse.ui.fragment.CartBottomSheetFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var themeToggleButton: ImageButton
    private lateinit var profileButton: ImageButton
    private lateinit var cartButton: ImageButton
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar
        setSupportActionBar(findViewById(R.id.toolbar))

        // Horní tlačítka
        themeToggleButton = findViewById(R.id.themeToggleButton)
        profileButton = findViewById(R.id.profileButton)
        cartButton = findViewById(R.id.cartButton)

        updateThemeIcon()

        themeToggleButton.setOnClickListener { toggleTheme() }

        profileButton.setOnClickListener {
            val userRepository = UserRepository(this)
            val currentUser = userRepository.getCurrentUser()

            if (currentUser != null) {
                navController.navigate(R.id.profileFragment)
            } else {
                navController.navigate(R.id.loginFragment)
            }
        }

        cartButton.setOnClickListener {
            val cartBottomSheet = CartBottomSheetFragment()
            cartBottomSheet.show(supportFragmentManager, cartBottomSheet.tag)
        }

        // Navigace
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        // Všechny záložky spodní navigace jsou top-level
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.menuFragment,
                R.id.catsFragment,
                R.id.adoptionFragment,
                R.id.shelterFragment
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun updateThemeIcon() {
        val isDarkMode =
            AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES

        themeToggleButton.setImageResource(
            if (isDarkMode) R.drawable.bright else R.drawable.ic_moon
        )

        val tintColor = if (isDarkMode) android.R.color.white else android.R.color.black
        themeToggleButton.imageTintList = ColorStateList.valueOf(getColor(tintColor))
    }

    private fun toggleTheme() {
        val currentMode = AppCompatDelegate.getDefaultNightMode()
        val newMode =
            if (currentMode == AppCompatDelegate.MODE_NIGHT_YES)
                AppCompatDelegate.MODE_NIGHT_NO
            else
                AppCompatDelegate.MODE_NIGHT_YES

        AppCompatDelegate.setDefaultNightMode(newMode)
        updateThemeIcon()
        Toast.makeText(this, "Motiv byl změněn", Toast.LENGTH_SHORT).show()
    }
}
