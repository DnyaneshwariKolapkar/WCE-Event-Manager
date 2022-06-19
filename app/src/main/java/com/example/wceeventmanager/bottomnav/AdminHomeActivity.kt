package com.example.wceeventmanager.bottomnav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.wceeventmanager.R
import com.example.wceeventmanager.databinding.ActivityAdminHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminHomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminHomeBinding
    var appBarConfiguration : AppBarConfiguration?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bundles
        val bundle = intent.extras

        val navView : BottomNavigationView = binding.bottomNavbar

        val navController = findNavController(R.id.main_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home, R.id.calender, R.id.profile
            )

        )

        Toast.makeText(this, "${bundle?.getBoolean("isAdminUser")} + ${bundle?.getBoolean("isClubUser")} ${bundle?.getBoolean("isVerified")}", Toast.LENGTH_SHORT).show()

//        val profileBundle = Bundle()
//        val eventListFragment = ProfileFragment()
//
//        profileBundle.putString("name", bundle?.getString("name"))
//        profileBundle.putString("email", bundle?.getString("email"))
//        profileBundle.putBoolean("isVerified", bundle!!.getBoolean("isVerified"))
//        profileBundle.putBoolean("isAdminUser", bundle.getBoolean("isAdminUser"))
//        profileBundle.putBoolean("isClubUser", bundle.getBoolean("isClubUser"))
//        profileBundle.putString("token", bundle.getString("token"))

//        if(bundle.getBoolean("isVerified")) {
//            eventListFragment.arguments = profileBundle
//
//            //supportFragmentManager.beginTransaction().add(R.id.home, eventListFragment).commit()
//
//            Toast.makeText(this, "${eventListFragment.arguments} ${bundle.getString("name")}", Toast.LENGTH_LONG).show()
//        }

        var isGuest = bundle?.getBoolean("isGuest")
        if(isGuest == true){
            navView.menu.removeItem(R.id.profile)

            Toast(this).apply {
                duration = Toast.LENGTH_SHORT
                view = layoutInflater.inflate(R.layout.custom_toast_guest_welcome,null)

            }.show()
        }
//        else {
        setupActionBarWithNavController(navController, appBarConfiguration!!)
        navView.setupWithNavController(navController)
//        }
        supportActionBar!!.hide()
    }

    fun getUserData(): Bundle? {
        val bundle = intent.extras
        return bundle
    }
}