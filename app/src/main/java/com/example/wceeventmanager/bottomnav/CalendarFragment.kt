package com.example.wceeventmanager.bottomnav

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.wceeventmanager.R
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

        val calender= binding?.cal
        var date = ""

        val bundle = Bundle()

        calender?.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            var the_real_month = month + 1
            var the_real_day = dayOfMonth
            var the_real_year = year

            if (the_real_day < 10) {
                the_real_day = ("0$the_real_day").toInt()
            }

            if (the_real_month < 10) {
                the_real_month = ("0$the_real_month").toInt()
            }

            date = "$the_real_month/$the_real_day/$the_real_year"
        }

        val sharedPref = activity?.getSharedPreferences("userInfo", Context.MODE_PRIVATE)!!
        val user = if(sharedPref.getBoolean("isVerified", false) || sharedPref.getBoolean("isAdmin", false)) "allowed" else "block"

        if(user == "allowed"){
            binding?.fab?.setOnClickListener{
                val fragment: Fragment = RegistrationFormOneFragment()
                //take date from calenderView and send to RegistrationFormOne
                bundle.putString("date",date)
                fragment.arguments = bundle


                val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(com.example.wceeventmanager.R.id.main_fragment, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }
        else{
            Toast.makeText(requireContext(), "User not allowed", Toast.LENGTH_SHORT).show()
        }
        
        return binding?.root
    }
}