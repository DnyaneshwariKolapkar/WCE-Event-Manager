package com.example.wceeventmanager

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.wceeventmanager.screens.ConfusedScreens
import com.example.wceeventmanager.screens.GetStartedScreen
import com.example.wceeventmanager.screens.TimeMgmtScreen

class ViewPagerScreens : AppCompatActivity() {
    private val fragment1 = ConfusedScreens()
    private val fragment2 = TimeMgmtScreen()
    private val fragment3 = GetStartedScreen()

    lateinit var adapter: MyPagerAdapter
    lateinit var activity : Activity

    lateinit var preference: SharedPreferences
    var pref_show_intro = "Intro"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_screens)

        supportActionBar!!.hide()
        activity = this
        preference = getSharedPreferences("introSlider", Context.MODE_PRIVATE)
        if(!preference.getBoolean(pref_show_intro,true)){
            startActivity(Intent(activity,WelcomeActivity ::class.java))
            finish()
        }

        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        val btnNext = findViewById<Button>(R.id.btn_next)
        val indicator1 = findViewById<Button>(R.id.indicator1)
        val indicator2 = findViewById<Button>(R.id.indicator2)
        val indicator3 = findViewById<Button>(R.id.indicator3)

        adapter = MyPagerAdapter(supportFragmentManager)
        adapter.list.add(fragment1)
        adapter.list.add(fragment2)
        adapter.list.add(fragment3)
        viewPager.adapter = adapter

        btnNext.setOnClickListener{
            viewPager.currentItem++
        }

        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {

            }

            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                if(position == adapter.list.size-1){
                    btnNext.text = "Get Started"
                    btnNext.setOnClickListener{
                        startActivity(Intent(activity,WelcomeActivity ::class.java))
                        finish()
                        val editor = preference.edit()
                        editor.putBoolean(pref_show_intro,false)
                        editor.apply()
                    }
                }else{
                    btnNext.text = "Next"
                    btnNext.setOnClickListener{
                        viewPager.currentItem++
                    }
                }
                when(viewPager.currentItem){
                    0->{
                        indicator1.setBackgroundColor(Color.rgb(255, 121, 23))
                        indicator2.setBackgroundColor(Color.LTGRAY)
                        indicator3.setBackgroundColor(Color.LTGRAY)
                    }
                    1->{
                        indicator1.setBackgroundColor(Color.LTGRAY)
                        indicator2.setBackgroundColor(Color.rgb(255, 121, 23))
                        indicator3.setBackgroundColor(Color.LTGRAY)
                    }
                    2->{
                        indicator1.setBackgroundColor(Color.LTGRAY)
                        indicator2.setBackgroundColor(Color.LTGRAY)
                        indicator3.setBackgroundColor(Color.rgb(255, 121, 23))

                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

    }
    class MyPagerAdapter(manager : FragmentManager) : FragmentPagerAdapter(manager){
        val list :  MutableList<Fragment> = ArrayList()
        override fun getCount(): Int {
            return list.size
        }

        override fun getItem(position: Int): Fragment {
            return list[position]
        }

    }
}