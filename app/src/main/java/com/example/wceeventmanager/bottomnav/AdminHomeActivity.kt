package com.example.wceeventmanager.bottomnav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.wceeventmanager.R
import com.example.wceeventmanager.databinding.ActivityAdminHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import nl.joery.animatedbottombar.AnimatedBottomBar

class AdminHomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView : BottomNavigationView = binding.bottomNavbar

        val navController = findNavController(R.id.main_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home, R.id.calender, R.id.profile
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        supportActionBar!!.hide()
    }
}