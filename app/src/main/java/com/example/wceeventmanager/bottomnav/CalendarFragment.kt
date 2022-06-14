package com.example.wceeventmanager.bottomnav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.wceeventmanager.RegistrationFormOneFragment
import com.example.wceeventmanager.databinding.FragmentCalendarBinding


class CalendarFragment : Fragment() {
    private var mbinding: FragmentCalendarBinding? = null
    private val binding get() = mbinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mbinding = FragmentCalendarBinding.inflate(inflater, container, false)

        val animation = AnimationUtils.loadAnimation(context, com.example.wceeventmanager.R.anim.move)
        binding?.txt1?.startAnimation(animation)

        var calender= binding?.cal

        binding?.fab?.setOnClickListener {
//            val registrationForm = Registration_Form()
//            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()
//            transaction.replace(R.id.main_fragment,registrationForm)
//            transaction.commit()

            
            val fragment: Fragment = RegistrationFormOneFragment()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(com.example.wceeventmanager.R.id.main_fragment, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        return binding?.root
    }
}