package com.example.wceeventmanager

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.wceeventmanager.bottomnav.CalendarFragment
import com.example.wceeventmanager.bottomnav.RegistrationFormTwoFragment
import com.example.wceeventmanager.databinding.FragmentRegistrationFormOneBinding


class RegistrationFormOneFragment : Fragment() {

    private var mbinding: FragmentRegistrationFormOneBinding?=null

    private val binding get()= mbinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mbinding= FragmentRegistrationFormOneBinding.inflate(inflater,container,false)

        val animation = AnimationUtils.loadAnimation(context, R.anim.move)
        mbinding?.txt1?.startAnimation(animation)

        //get date from calender fragment
        val date = binding.Date
        val args = this.arguments
        val selecteddate = args?.get("date")

        date.text = selecteddate.toString()
        date.setTextColor(Color.BLACK)
        date.typeface = Typeface.DEFAULT_BOLD


        val eventName = binding.EditTextEventName.text.toString()
        var eventType = ""
        val startTime = binding.EditTextStartTime.text.toString()
        val duration = binding.EditTextDuration.text.toString()
        binding.DropDownMenuBar.addTextChangedListener {
            eventType = binding.DropDownMenuBar.text.toString()
        }

        //dropdownMenu for select event type
        val eventtypes = resources.getStringArray(R.array.events)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdownmenu,eventtypes)
        binding.DropDownMenuBar.setAdapter(arrayAdapter)

        // Next button - RegistrationFormTwoFragment
        val nextBTN = mbinding?.fabNext
        nextBTN?.setOnClickListener {
            val fragment = RegistrationFormTwoFragment()
            val bundle = Bundle()
            if( TextUtils.isEmpty(binding.EditTextEventName.text.toString())
                ||TextUtils.isEmpty(binding.EditTextStartTime.text.toString())
                ||TextUtils.isEmpty(binding.EditTextDuration.text.toString())) {
                Toast.makeText(context,"Enter All Fields !!",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{

                bundle.putString("eventName", eventName)
                bundle.putString("eventType", eventType)
                bundle.putString("startTime", startTime)
                bundle.putString("duration", duration)
                fragment.arguments = bundle
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.main_fragment, fragment)?.addToBackStack(null)?.commit()
                 }
        }

        // Back button - CalendarFragment
        val backBTN = binding.backForm1
        backBTN.setOnClickListener {
            val fragment = CalendarFragment()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.main_fragment, fragment)?.addToBackStack(null)?.commit()
        }
        return mbinding!!.root
    }

    override fun onDestroy() {
        super.onDestroy()
        mbinding = null
    }


}