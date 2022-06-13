package com.example.wceeventmanager.bottomnav

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.wceeventmanager.databinding.FragmentEventListBinding

class EventListFragment : Fragment() {
    private lateinit var mBinding: FragmentEventListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentEventListBinding.inflate(inflater, container, false)

        //In case Api down just comment getData()
        getData()

        return mBinding.root
    }

    fun getData(){
        val listOfEvents = ArrayList<Event>()

        val apiReq = JsonArrayRequest("https://walchand-event-organizer.herokuapp.com/getEvents",{
            for(i in 0 until it.length()){
                val obj = it.getJSONObject(i)

                // Event name
                val eventName = obj.getString("eventname")
                Log.e("eventName", eventName)
                val clubName = "null"

                // Branch tags (if any)
                val tagsJSONBranch = obj.getJSONArray("branches")
                val tagsBranch = ArrayList<String>()

                for(j in 0 until tagsJSONBranch.length()){
                    val str = tagsJSONBranch.getJSONObject(j).getString("branch")
                    tagsBranch.add(str.toString())
                }

                // Event tags (if any)
                val tagsJSONAOI = obj.getJSONArray("areaofinterest")
                val tagsAOI = ArrayList<String>()
                for(j in 0 until tagsJSONAOI.length()){
                    val str = tagsJSONAOI.getJSONObject(j).getString("index")
                    tagsAOI.add(str.toString())
                }

                // Event date and time
                val eventTime = obj.getString("starttime")
                val eventDate = obj.getString("date")

                // Event mode
                val eventMode = obj.getString("mode")

                listOfEvents.add(Event(eventName, clubName, tagsBranch, tagsAOI, eventTime, eventDate, eventMode))
            }

            val event_recy = mBinding.eventRecyView
            val Adapter = RecyclerAdapter(listOfEvents, requireContext())

            event_recy.adapter = Adapter

            event_recy.layoutManager = LinearLayoutManager(requireContext())
        },{

        })

        val queue = Volley.newRequestQueue(requireContext())
        queue.add(apiReq)
    }
}