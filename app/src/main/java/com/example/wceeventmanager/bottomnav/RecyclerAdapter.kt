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
        var eventTime = view.findViewById<TextView>(R.id.event_time)
        var eventDate = view.findViewById<TextView>(R.id.event_date)

        var branch1 = view.findViewById<TextView>(R.id.branch1)
        var branch2 = view.findViewById<TextView>(R.id.branch2)
        var branch3 = view.findViewById<TextView>(R.id.branch3)
        var branch4 = view.findViewById<TextView>(R.id.branch4)
        var branch5 = view.findViewById<TextView>(R.id.branch5)
        var branch6 = view.findViewById<TextView>(R.id.branch6)

        var aoi1 = view.findViewById<TextView>(R.id.aoi1)
        var aoi2 = view.findViewById<TextView>(R.id.aoi2)
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.myViewHolder {
        return myViewHolder(LayoutInflater.from(context).inflate(R.layout.event_list, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.myViewHolder, position: Int) {
        var model = eventList[position]



        holder.eventName.text = model.eventName
        holder.clubName.text = model.clubName

        // branch tags
        val branches = model.tagsBranch.size
        if(branches == 1){
            holder.branch1.text = model.tagsBranch[0]
            holder.branch2.visibility = View.GONE
            holder.branch3.visibility = View.GONE
            holder.branch4.visibility = View.GONE
            holder.branch5.visibility = View.GONE
            holder.branch6.visibility = View.GONE
        }

        if(branches == 2){
            holder.branch1.text = model.tagsBranch[0]
            holder.branch2.text = model.tagsBranch[1]
            holder.branch3.visibility = View.GONE
            holder.branch4.visibility = View.GONE
            holder.branch5.visibility = View.GONE
            holder.branch6.visibility = View.GONE
        }

        if(branches == 3){
            holder.branch1.text = model.tagsBranch[0]
            holder.branch2.text = model.tagsBranch[1]
            holder.branch3.text = model.tagsBranch[2]
            holder.branch4.visibility = View.GONE
            holder.branch5.visibility = View.GONE
            holder.branch6.visibility = View.GONE
        }

        if(branches == 4){
            holder.branch1.text = model.tagsBranch[0]
            holder.branch2.text = model.tagsBranch[1]
            holder.branch3.text = model.tagsBranch[2]
            holder.branch4.text = model.tagsBranch[3]
            holder.branch5.visibility = View.GONE
            holder.branch6.visibility = View.GONE
        }

        if(branches == 5){
            holder.branch1.text = model.tagsBranch[0]
            holder.branch2.text = model.tagsBranch[1]
            holder.branch3.text = model.tagsBranch[2]
            holder.branch4.text = model.tagsBranch[3]
            holder.branch5.text = model.tagsBranch[4]
            holder.branch6.visibility = View.GONE
        }

        if(branches == 6){
            holder.branch1.text = model.tagsBranch[0]
            holder.branch2.text = model.tagsBranch[1]
            holder.branch3.text = model.tagsBranch[2]
            holder.branch4.text = model.tagsBranch[3]
            holder.branch5.text = model.tagsBranch[4]
            holder.branch6.text = model.tagsBranch[5]
        }

        // aoi tags
        val aois = model.tagsAOI.size
        if(aois == 1){
            holder.aoi1.text = model.tagsAOI[0]
            holder.aoi2.visibility = View.GONE
        }

        if(aois == 2){
            holder.aoi1.text = model.tagsAOI[0]
            holder.aoi2.text = model.tagsAOI[1]
        }

        holder.eventTime.text = model.eventTime
        holder.eventDate.text = model.eventDate
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

}