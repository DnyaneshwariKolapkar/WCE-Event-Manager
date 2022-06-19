package com.example.wceeventmanager.bottomnav

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.replace
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyLog.TAG
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.wceeventmanager.R
import com.example.wceeventmanager.RegistrationFormOneFragment
import com.example.wceeventmanager.databinding.FragmentRegistrationForm2Binding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class RegistrationFormTwoFragment : Fragment() {

    private var mbinding: FragmentRegistrationForm2Binding?=null

    private val binding get()= mbinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mbinding= FragmentRegistrationForm2Binding.inflate(inflater,container,false)
        val prevData = arguments

        //Added animation
        val animation = AnimationUtils.loadAnimation(context, com.example.wceeventmanager.R.anim.move)
        binding.txt1.startAnimation(animation)

        // Tags array - Area of Interest + Branches
        val tagsTopic = ArrayList<String>()
        val tagsBranch = ArrayList<String>()
        var allBranch = false

        val addAOI = binding.addAoi
        val nextBtn = binding.backForm2

        // Loading progress bar
        val progressBar = binding.loadingBar

        // Confirm button
        val confirm = binding.fabConfirm

        val lengthHandlerToast = layoutInflater.inflate(R.layout.custom_toast_tag_length, null)
        val duplicateHandlerToast = layoutInflater.inflate(R.layout.custom_toast_duplicate_tag, null)
        val emptyHandlerToast = layoutInflater.inflate(R.layout.custom_toast_empty_tag, null)
        if(binding.rbonline.isChecked){
            binding.EditTextMeetLink.isEnabled = true
        }


        addAOI.setOnClickListener(View.OnClickListener {
            if(tagsTopic.size > 1) {
                Toast(requireContext()).apply {
                    duration = Toast.LENGTH_SHORT
                    view = lengthHandlerToast
                }.show()

                binding.EditTextAOI.text?.clear()
            }else{
                val aoi = binding.EditTextAOI.text.toString()
    //                Toast.makeText(requireContext(), aoi, Toast.LENGTH_SHORT).show()

                // Check if the AOI is already in the list
                if(tagsTopic.contains(aoi)) {
                    Toast(requireContext()).apply {
                        duration = Toast.LENGTH_SHORT
                        view = duplicateHandlerToast
                    }.show()

                    binding.EditTextAOI.text?.clear()
                }
                // Empty check
                else if(TextUtils.isEmpty(binding.EditTextAOI.text.toString())){
                    Toast(requireContext()).apply {
                        duration = Toast.LENGTH_SHORT
                        view = emptyHandlerToast
                    }.show()
                } else{
                    // trim and add
                    aoi.trim()
                    tagsTopic.add(aoi)
                    binding.EditTextAOI.text?.clear()
                }
            }
        })

        val civil_cb = binding.cbCivil
        val mech_cb = binding.cbmech
        val ece_cb = binding.cbElectronics
        val ele_cb = binding.cbElectrical
        val cse_cb = binding.cbCSE
        val it_cb = binding.cbIT

        // Mode of event
        val onlineMode = binding.rbonline
        val offlineMode = binding.rboffline
        val hybridMode = binding.rbhybrid

        var mode = ""

        // Data from previous fragment - RegistrationFormOneFragment
        var eventName = prevData?.getString("eventName")
        if(eventName == null) eventName = "N/A"

        var eventType = prevData?.getString("eventType")
        if(eventType == null) eventType = "N/A"

        var eventDate = prevData?.getString("date")
        if(eventDate == null) eventDate = "N/A"

        var startTime = prevData?.getString("startTime")
        if(startTime == null) startTime = "N/A"

        var duration = prevData?.getString("duration")
        if(duration == null) duration = "N/A"

        // Back Button - Back to Registration Form 1
        val backBtn = binding.backForm2
        backBtn.setOnClickListener(View.OnClickListener {
            val fragment = RegistrationFormOneFragment()
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_fragment, fragment).commit()
        })

        // Mode of event and Meet Link + Venue
        var meetLinkText = binding.EditTextMeetLink
        var venueText = binding.EditTextVenue

        // Radio Button Checked Listener
        onlineMode.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                meetLinkText.visibility = View.VISIBLE
                venueText.visibility = View.GONE
                mode = "Online"
                Toast.makeText(requireContext(), "Online Mode", Toast.LENGTH_SHORT).show()
            }
        }

        offlineMode.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                meetLinkText.visibility = View.GONE
                venueText.visibility = View.VISIBLE
                mode = "Offline"
                Toast.makeText(requireContext(), "Offline Mode", Toast.LENGTH_SHORT).show()
            }
        }

        hybridMode.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                meetLinkText.visibility = View.VISIBLE
                venueText.visibility = View.VISIBLE
                mode = "Hybrid"
                Toast.makeText(requireContext(), "Hybrid Mode", Toast.LENGTH_SHORT).show()
            }
        }

        // Adding data to the database
        confirm.setOnClickListener {
            // Branches checkbox
            if(civil_cb.isChecked)
            {
                tagsBranch.add("Civil")
            }

            if(mech_cb.isChecked)
            {
                tagsBranch.add("Mechanical")
            }

            if(ece_cb.isChecked)
            {
                tagsBranch.add("Electronics")
            }

            if(ele_cb.isChecked)
            {
                tagsBranch.add("Electrical")
            }

            if(cse_cb.isChecked)
            {
                tagsBranch.add("CSE")
            }

            if(it_cb.isChecked)
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
            // JSON array of Branches
            val jsonArrayBranch = JSONArray()
            for(i in tagsBranch){
                val obj = JSONObject()
                try{
                    obj.put("branch", i)
                } catch(e: JSONException){
                    e.printStackTrace()
                }

                jsonArrayBranch.put(obj)
            }

            // JSON array of AOI
            val jsonArrayAOI = JSONArray()
            for(i in tagsTopic){
                val obj = JSONObject()
                try{
                    obj.put("index", i)
                } catch(e: JSONException){
                    e.printStackTrace()
                }

                jsonArrayAOI.put(obj)
            }

            var meetLink = ""
            var venue = ""
            if(meetLinkText.isEnabled){
                meetLink = meetLinkText.text.toString()
            }

            if(venueText.isEnabled){
                venue = venueText.text.toString()
            }

            // Loading bar - Showing
            progressBar.visibility = View.VISIBLE

            // Adding data to AddEvent class
            val eventData = AddEvent(eventName, eventType, eventDate, startTime, duration.toString(), jsonArrayAOI, jsonArrayBranch, mode, meetLink, venue)

            postData(eventData)
        }

        return mbinding!!.root
    }

    private fun postData(eventData: AddEvent) {
        Toast.makeText(requireContext(), eventData.eventName + " " + eventData.eventDate + " " + eventData.eventType + " " + eventData.startTime + " " + eventData.duration + " " + eventData.AOI + " " + eventData.Branches + " " + eventData.mode + " " + eventData.meetLink + " " + eventData.venue, Toast.LENGTH_LONG).show()

        val apiUrl = "https://walchand-event-organizer.herokuapp.com/insertevent"

        // Volley post request to add data to the database
        var volleyRequestQueue: RequestQueue? = null
        volleyRequestQueue = Volley.newRequestQueue(requireContext())

        // Loading bar - Hiding
        val progressBar = binding.loadingBar
        progressBar.visibility = View.GONE

        // Mutable map of data to be sent to the server
        val parameters :  MutableMap<Any, Any> = HashMap()
        parameters["eventname"] = eventData.eventName
        parameters["eventtype"] = eventData.eventType
        parameters["date"] = eventData.eventDate
        parameters["starttime"] = eventData.startTime
        parameters["duration"] = eventData.duration
        parameters["areaofinterest"] = eventData.AOI
        parameters["branches"] = eventData.Branches
        parameters["mode"] = eventData.mode
        parameters["link"] = eventData.meetLink
        parameters["location"] = eventData.venue

        val jsonRequest : JsonObjectRequest = object : JsonObjectRequest(
            Method.POST, apiUrl, (parameters as Map<*, *>?)?.let { JSONObject(it) },
            Response.Listener {
                // Success
                Toast.makeText(requireContext(), "Event added successfully", Toast.LENGTH_SHORT).show()
                val fragment = EventListFragment()
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_fragment, fragment).commit()

                val intent = Intent(requireContext(), AdminHomeActivity::class.java)
                startActivity(intent)
            },
            Response.ErrorListener { error ->
                // Error
                Toast.makeText(requireContext(), "Error: " + error.message, Toast.LENGTH_LONG).show()
                //Back to the Main Fragment
                val fragment = EventListFragment()
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.profile, fragment).commit()

                val intent = Intent(requireContext(), AdminHomeActivity::class.java)
                startActivity(intent)
            }
        ) {

        }

//        val stringRequest : StringRequest = object : StringRequest(Request.Method.POST, apiUrl,
//            Response.Listener { response ->
//                try{
//                    val jsonObject = JSONObject(response)
//                    val success = jsonObject.getString("success")
//                    if(success.equals("1"))
//                    {
//                        Toast.makeText(requireContext(), "Event added successfully", Toast.LENGTH_SHORT).show()
//                        val fragment = RegistrationFormOneFragment()
//                        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_fragment, fragment).commit()
//                    }
//                    else
//                    {
//                        Toast.makeText(requireContext(), "Error adding event", Toast.LENGTH_SHORT).show()
//                    }
//                }
//                catch (e: JSONException)
//                {
//                    e.printStackTrace()
//                }
//            },
//            Response.ErrorListener { error -> Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show() }
//        ){
//            override fun getParams(): MutableMap<String, String> {
//                val params: MutableMap<String, String> = HashMap()
//                params["eventname"] = eventData.eventName
//                params["eventtype"] = eventData.eventType
//                params["date"] = eventData.eventDate
//                params["starttime"] = eventData.startTime
//                params["duration"] = eventData.duration
//                params["areaofinterest"] = eventData.AOI.toString()
//                params["branches"] = eventData.Branches.toString()
//                params["link"] = eventData.meetLink
//                params["location"] = eventData.venue
//                params["mode"] = eventData.mode
//
//                println(params)
//
//                return params
//            }
//        }

        volleyRequestQueue.add(jsonRequest)
//        volleyRequestQueue.add(stringRequest)
    }

}