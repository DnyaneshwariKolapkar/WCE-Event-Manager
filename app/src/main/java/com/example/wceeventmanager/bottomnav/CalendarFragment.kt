package com.example.wceeventmanager.bottomnav

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.wceeventmanager.R
import com.example.wceeventmanager.databinding.FragmentCalendarBinding

class CalendarFragment : Fragment() {
    private var mbinding: FragmentCalendarBinding?=null
    private val binding get() = mbinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mbinding = FragmentCalendarBinding.inflate(inflater,container,false)

        val animation = AnimationUtils.loadAnimation(context, R.anim.move)
        val animationleft = AnimationUtils.loadAnimation(context, R.anim.moveleft)
        binding?.txt1?.startAnimation(animation)

        binding?.btnWeek?.setOnClickListener {
            binding!!.btnMonth.setBackgroundColor(Color.rgb(255,255,255))
            binding!!.btnWeek.setBackgroundColor(Color.rgb(255,121,23))
            binding!!.btnWeek.setTextColor(Color.rgb(255,255,255))
            binding!!.btnMonth.setTextColor(Color.rgb(128,128,128))
            binding!!.btnWeek.startAnimation(animation)



        }
        binding?.btnMonth?.setOnClickListener {
            binding!!.btnWeek.setBackgroundColor(Color.rgb(255,255,255))
            binding!!.btnMonth.setBackgroundColor(Color.rgb(255,121,23))
            binding!!.btnMonth.setTextColor(Color.rgb(255,255,255))
            binding!!.btnWeek.setTextColor(Color.rgb(128,128,128))
            binding!!.btnMonth.startAnimation(animationleft)
        }


        return binding?.root
    }

}