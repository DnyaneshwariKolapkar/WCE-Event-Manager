package com.example.wceeventmanager.bottomnav

import android.app.Fragment
import android.content.Context
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.wceeventmanager.R
import com.example.wceeventmanager.databinding.FragmentEventListBinding

class RecyclerAdapter(private var eventList: ArrayList<Event>, var context: Context) : RecyclerView.Adapter<RecyclerAdapter.myViewHolder>() {

    class myViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var eventName = view.findViewById<TextView>(R.id.event_name)
        var clubName = view.findViewById<TextView>(R.id.club_name)
        var tag1 = view.findViewById<TextView>(R.id.tag1_text)
        var tag2 = view.findViewById<TextView>(R.id.tag2_text)
        var eventTime = view.findViewById<TextView>(R.id.event_time)
        var eventDate = view.findViewById<TextView>(R.id.event_date)
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.myViewHolder {
        return myViewHolder(LayoutInflater.from(context).inflate(R.layout.event_list, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.myViewHolder, position: Int) {
        var model = eventList[position]

        holder.eventName.text = model.eventName
        holder.clubName.text = model.clubName
        holder.tag1.text = model.tags[0]
        holder.tag2.text = model.tags[1]
        holder.eventTime.text = model.eventTime
        holder.eventDate.text = model.eventDate
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

}