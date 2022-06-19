package com.example.wceeventmanager

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.wceeventmanager.bottomnav.CalendarFragment
import com.example.wceeventmanager.bottomnav.RegistrationFormTwoFragment
import com.example.wceeventmanager.databinding.FragmentRegistrationFormOneBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat


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
        val selectedDate = args?.get("date")

        date.text = selectedDate.toString()
        date.setTextColor(Color.BLACK)
        date.typeface = Typeface.DEFAULT

        //dropdownMenu for select event type
        val eventtypes = resources.getStringArray(R.array.events)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdownmenu,eventtypes)
        binding.DropDownMenuBar.setAdapter(arrayAdapter)

        val startTime = binding.starttime
        var startTimeStr = ""
        val  setTimeBtn = binding.btnSetTime
        setTimeBtn.setOnClickListener{
            openTimePickerDialog()
        }

        // Next button - RegistrationFormTwoFragment
        val nextBTN = mbinding?.fabNext
        nextBTN?.setOnClickListener {

            val eventName = binding.EditTextEventName.text.toString()
            var eventType = ""
            val eventDuration = binding.EditTextDuration.text.toString()
            startTimeStr = startTime.text.toString()

            binding.DropDownMenuBar.addTextChangedListener {
                eventType = it.toString()
            }

            Toast.makeText(requireContext(), eventName + " " + selectedDate.toString() + " " + eventType + " " + eventDuration, Toast.LENGTH_LONG).show()

            val fragment = RegistrationFormTwoFragment()
            val bundle = Bundle()
            if( TextUtils.isEmpty(binding.EditTextEventName.text.toString())
                ||TextUtils.isEmpty(binding.EditTextDuration.text.toString())) {
                Toast.makeText(context,"Enter All Fields !!",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{

                bundle.putString("eventName", eventName)
                bundle.putString("eventType", eventType)
                bundle.putString("startTime", startTimeStr)
                bundle.putString("duration", eventDuration)
                bundle.putString("date", selectedDate.toString())

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

    private fun openTimePickerDialog() {
        // Checking if time format is 24 hours or 12 hours and then setting time format
        val isSystem24HourFormat = is24HourFormat(requireContext())
        val clockFormat = if (isSystem24HourFormat) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Set start time of the event")
            .build()

        picker.show(childFragmentManager, "TIME_PICKER")

        picker.addOnPositiveButtonClickListener {
            val h = picker.hour.toString()
            val m = picker.minute.toString()

            var startTime = "$h:$m"
            if(h.length == 1 && m.length == 1){
                startTime = "0$h:0$m"
            }
            else if(h.length == 1){
                startTime = "0$h:$m"
            }
            else if(m.length == 1){
                startTime = "$h:0$m"
            }

            binding.starttime.text = startTime
            Log.d("startTime", startTime)
        }
        picker.addOnNegativeButtonClickListener {
            Log.d("TimePicker", "Cancelled")
        }
        picker.addOnCancelListener {
            Log.d("TimePicker", "Cancelled")
        }
        picker.addOnDismissListener {
            Log.d("TimePicker", "Dismissed")
        }
    }
}