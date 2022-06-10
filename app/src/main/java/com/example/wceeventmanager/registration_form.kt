package com.example.wceeventmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wceeventmanager.databinding.FragmentRegistrationFormBinding



class registration_form : Fragment() {

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

        return mbinding!!.root
    }


}