package com.example.wceeventmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wceeventmanager.bottomnav.AdminHomeActivity
import com.example.wceeventmanager.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.signup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.guest.setOnClickListener {
            startActivity(Intent(this, AdminHomeActivity::class.java))
        }

        supportActionBar!!.hide()
    }
}