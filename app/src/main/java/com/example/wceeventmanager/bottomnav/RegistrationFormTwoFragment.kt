package com.example.wceeventmanager.bottomnav

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyLog.TAG
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.wceeventmanager.R
import com.example.wceeventmanager.RegistrationFormOneFragment
import com.example.wceeventmanager.databinding.FragmentRegistrationForm2Binding


class RegistrationFormTwoFragment : Fragment() {

    private var mbinding: FragmentRegistrationForm2Binding?=null

    private val binding get()= mbinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mbinding= FragmentRegistrationForm2Binding.inflate(inflater,container,false)
        val prevData = arguments

        //Added animation
        val animation = AnimationUtils.loadAnimation(context, com.example.wceeventmanager.R.anim.move)
        binding?.txt1?.startAnimation(animation)

        // Tags array - Area of Interest + Branches
        val tagsTopic = ArrayList<String>()
        val tagsBranch = ArrayList<String>()
        var allBranch = false

        val addAOI = binding?.addAoi
        var meetLink = binding?.EditTextMeetLink?.text.toString()
        var venue = binding?.EditTextVenue?.text.toString()
        val nextBtn = binding?.backForm2

        // Confirm button
        val confirm = binding?.fabConfirm

        val lengthHandlerToast = layoutInflater.inflate(R.layout.custom_toast_tag_length, null)
        val duplicateHandlerToast = layoutInflater.inflate(R.layout.custom_toast_duplicate_tag, null)
        val emptyHandlerToast = layoutInflater.inflate(R.layout.custom_toast_empty_tag, null)
        if(binding?.rbonline?.isChecked == true){
            binding?.EditTextMeetLink?.isEnabled = true
        }
        nextBtn?.setOnClickListener {

        }

        addAOI?.setOnClickListener(View.OnClickListener {
           if(tagsTopic.size > 1)
           {
               Toast(requireContext()).apply {
                   duration = Toast.LENGTH_SHORT
                   view = lengthHandlerToast
               }.show()

               binding?.EditTextAOI?.text?.clear()
           }else{
               val aoi = binding?.EditTextAOI?.text.toString()
//                Toast.makeText(requireContext(), aoi, Toast.LENGTH_SHORT).show()

               // Check if the AOI is already in the list
                if(tagsTopic.contains(aoi))
                {
                     Toast(requireContext()).apply {
                          duration = Toast.LENGTH_SHORT
                          view = duplicateHandlerToast
                     }.show()

                    binding?.EditTextAOI?.text?.clear()
                }
               // Empty check
               else if(TextUtils.isEmpty(binding?.EditTextAOI?.text.toString())){
                    Toast(requireContext()).apply {
                        duration = Toast.LENGTH_SHORT
                        view = emptyHandlerToast
                    }.show()
               }

                else{
                    // trim and add
                    aoi.trim()
                    tagsTopic.add(aoi)
                    binding?.EditTextAOI?.text?.clear()
                }
           }
        })

        val civil_cb = binding?.cbCivil
        val mech_cb = binding?.cbmech
        val ece_cb = binding?.cbElectronics
        val ele_cb = binding?.cbElectrical
        val cse_cb = binding?.cbCSE
        val it_cb = binding?.cbIT

        if(civil_cb?.isChecked == true)
        {
            tagsBranch.add("Civil")
        }

        if(mech_cb?.isChecked == true)
        {
            tagsBranch.add("Mechanical")
        }

        if(ece_cb?.isChecked == true)
        {
            tagsBranch.add("Electronics")
        }

        if(ele_cb?.isChecked == true)
        {
            tagsBranch.add("Electrical")
        }

        if(cse_cb?.isChecked == true)
        {
            tagsBranch.add("CSE")
        }

        if(it_cb?.isChecked == true)
        {
            tagsBranch.add("IT")
        }

        if(tagsBranch.size == 6) allBranch = true

        // null check
        if(tagsTopic.size == 0)
        {
            tagsTopic.add("")
        }

        if(tagsBranch.size == 0)
        {
            tagsBranch.add("")
        }

        // If meet link is empty then add the default value
        if(meetLink.isEmpty()) meetLink = "N/A"

        // If venue is empty then add the default value
        if(venue.isEmpty()) venue = "N/A"

        var eventName = ""
        var eventType = ""
        var eventDate = ""
        var startTime = ""
        var duration = ""

        if(prevData?.getString("event_name") != null)
        {
           eventName = prevData.getString("event_name")!!
        }

        if(prevData?.getString("event_type") != null)
        {
            eventType = prevData.getString("event_type")!!
        }

        if(prevData?.getString("event_date") != null)
        {
            eventDate = prevData.getString("event_date")!!
        }

        if(prevData?.getString("start_time") != null)
        {
            startTime = prevData.getString("start_time")!!
        }

        if(prevData?.getString("duration") != null)
        {
            duration = prevData.getString("duration")!!
        }

        // Back Button - Back to Registration Form 1
        val backBtn = binding?.backForm2
        backBtn?.setOnClickListener(View.OnClickListener {
            val fragment = RegistrationFormOneFragment()
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_fragment, fragment).commit()
        })

        // Adding data to AddEvent class
        val eventData = AddEvent(eventName, eventType, eventDate, startTime, duration, tagsTopic, tagsBranch, meetLink, venue)

        // Adding data to the database
        confirm?.setOnClickListener { postData(eventData) }

        return mbinding!!.root
    }

    private fun postData(eventData: AddEvent) {
        val apiUrl = "https://walchand-event-organizer.herokuapp.com/insertevent"

        // loading dialog
        val loading = mbinding?.loadingBar
        loading?.visibility = View.VISIBLE

        // Volley post request to add data to the database
        var volleyRequestQueue: RequestQueue? = null
        volleyRequestQueue = Volley.newRequestQueue(requireContext())

        val parameters = HashMap<String, String>()
        parameters["eventName"] = eventData.eventName
        parameters["eventType"] = eventData.eventType
        parameters["eventDate"] = eventData.eventDate
        parameters["startTime"] = eventData.startTime
        parameters["duration"] = eventData.duration
        parameters["tagsTopic"] = eventData.AOI.toString()
        parameters["tagsBranch"] = eventData.Branches.toString()
        parameters["meetLink"] = eventData.meetLink
        parameters["venue"] = eventData.venue

        val strReq: StringRequest =
            object : StringRequest(Method.POST, apiUrl, Response.Listener {
                // Hide the loading dialog
                loading?.visibility = View.GONE

                // Server Response Handling
                try{
                    val response = it.toString()

                }
                catch(e : Exception){
                    Toast.makeText(requireContext(), "Error : $e", Toast.LENGTH_SHORT).show()
                }


            }, Response.ErrorListener {volleyError -> // error occurred
                Log.e(TAG, "problem occurred, volley error: " + volleyError.message)

            }) {
                override fun getParams(): MutableMap<String, String> {
                    return parameters
                }
            }

        volleyRequestQueue.add(strReq)
        Toast.makeText(requireContext(), "Event added successfully", Toast.LENGTH_SHORT).show()

        // Back to the Main Fragment
        val fragment = EventListFragment()
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_fragment, fragment).commit()

        // Clear the data
        binding?.EditTextAOI?.text?.clear()
        binding?.EditTextVenue?.text?.clear()
        binding?.EditTextMeetLink?.text?.clear()

    }

}