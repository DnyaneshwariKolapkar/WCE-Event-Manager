package com.example.wceeventmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wceeventmanager.bottomnav.CalendarFragment
import com.example.wceeventmanager.databinding.FragmentRegistrationFormBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class RegistrationFormOneFragment : Fragment() {

    private var mbinding: FragmentRegistrationFormBinding?=null

    private val binding get()= mbinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mbinding= FragmentRegistrationFormBinding.inflate(inflater,container,false)

        val eventName = binding?.eventname.toString()
        val eventType = binding?.eventtype.toString()
        val startTime = binding?.starttime.toString()
        val duration = binding?.Duration.toString()
        val eventDate = binding?.Date.toString()

        // Next button - RegistrationFormTwoFragment
        val nextBTN = binding?.fabNext
        nextBTN?.setOnClickListener {
            val fragment = RegistrationFormTwoFragment()
            val bundle = Bundle()

            bundle.putString("eventName", eventName)
            bundle.putString("eventType", eventType)
            bundle.putString("startTime", startTime)
            bundle.putString("duration", duration)
            bundle.putString("eventDate", eventDate)
            fragment.arguments = bundle

            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.main_fragment, fragment)?.addToBackStack(null)?.commit()
        }

        // Back button - CalendarFragment
        val backBTN = binding?.backForm1
        backBTN?.setOnClickListener {
            val fragment = CalendarFragment()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.main_fragment, fragment)?.addToBackStack(null)?.commit()
        }

        return mbinding!!.root
    }


}