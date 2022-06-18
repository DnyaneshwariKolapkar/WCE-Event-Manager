package com.example.wceeventmanager

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.wceeventmanager.bottomnav.CalendarFragment
import com.example.wceeventmanager.databinding.FragmentRegistrationFormOneBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class RegistrationFormOneFragment : Fragment() {

    private var mbinding: FragmentRegistrationFormOneBinding?=null

    private val binding get()= mbinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mbinding= FragmentRegistrationFormOneBinding.inflate(inflater,container,false)

        val eventName = binding?.EditTextEventName?.text.toString()
        var eventType = ""
        val startTime = binding?.EditTextStartTime?.text.toString()
        val duration = binding?.EditTextDuration?.text.toString()
        binding?.DropDownMenuBar?.addTextChangedListener {
            eventType = binding?.DropDownMenuBar?.text.toString()
        }


        //dropdownMenu for select event type
        val events = resources.getStringArray(R.array.events)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdownitem,events)
        binding?.DropDownMenuBar?.setAdapter(arrayAdapter)

        // Next button - RegistrationFormTwoFragment
        val nextBTN = mbinding?.fabNext
        nextBTN?.setOnClickListener {
            val fragment = RegistrationFormTwoFragment()
            val bundle = Bundle()
            if( TextUtils.isEmpty(binding?.EditTextEventName?.text.toString())
                ||TextUtils.isEmpty(binding?.EditTextStartTime?.text.toString())
                ||TextUtils.isEmpty(binding?.EditTextDuration?.text.toString())) {
                Toast.makeText(context,"Enter All Fields !!",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{
                Toast.makeText(context,eventType,Toast.LENGTH_SHORT).show()
                bundle.putString("eventName", eventName)
                //  bundle.putString("eventType", eventType)
                bundle.putString("startTime", startTime)
                bundle.putString("duration", duration)
                fragment.arguments = bundle
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.main_fragment, fragment)?.addToBackStack(null)?.commit()

                 }

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