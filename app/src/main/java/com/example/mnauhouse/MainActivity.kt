package com.example.mnauhouse

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    // Публичное свойство для доступа из Fragment
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Находим Toolbar и устанавливаем его как главный ActionBar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false) // Отключаем стандартный заголовок

        // 2. Находим NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        // 3. AppBarConfiguration
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.menuFragment, R.id.catsFragment, R.id.adoptionFragment, R.id.profileFragment
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)

        // 4. Кнопка профиля: Проверяем вход
        val profileButton = findViewById<ImageButton>(R.id.profileButton)
        profileButton.setOnClickListener {
            val userRepository = com.example.mnauhouse.data.repository.UserRepository(this)
            val currentUser = userRepository.getCurrentUser()
            if (currentUser != null) {
                // Уже вошел — перейти в кабинет
                bottomNavigationView.selectedItemId = R.id.profileFragment
            } else {
                // Не вошел — перейти в LoginFragment
                navController.navigate(R.id.loginFragment)
            }
        }

        // 5. Кнопка корзины
        val cartButton = findViewById<ImageButton>(R.id.cartButton)
        cartButton.setOnClickListener {
            android.widget.Toast.makeText(this, "Košík bude přidán", android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}