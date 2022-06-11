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


        return binding?.root
    }

}