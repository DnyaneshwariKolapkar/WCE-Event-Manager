package com.example.wceeventmanager.bottomnav

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.wceeventmanager.R
import com.example.wceeventmanager.databinding.FragmentEventListBinding
import org.json.JSONArray

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

                val eventName = obj.getString("eventname")
                Log.e("eventName", eventName)
                val clubName = "null"

                val tagsJSON = obj.getJSONArray("branches")
                val tags = ArrayList<String>()
                for(j in 0 until tagsJSON.length()){
                    val str = tagsJSON.getJSONObject(j).getString("branch")
                    tags.add(str.toString())
                }

                val eventTime = obj.getString("starttime")
                val eventDate = obj.getString("date")

                listOfEvents.add(Event(eventName, clubName, tags, eventTime, eventDate))
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