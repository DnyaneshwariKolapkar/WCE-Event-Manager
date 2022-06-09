package com.example.wceeventmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    //delay time
    private val splashScreenTime : Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val background: ImageView = findViewById(R.id.logo)
        val animation = AnimationUtils.loadAnimation(this,R.anim.fade)
        background.startAnimation(animation)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            //redirect to welcome activity
            startActivity(Intent(this,ViewPagerScreens::class.java))
            finish()
        },splashScreenTime)

        supportActionBar!!.hide()
    }
}