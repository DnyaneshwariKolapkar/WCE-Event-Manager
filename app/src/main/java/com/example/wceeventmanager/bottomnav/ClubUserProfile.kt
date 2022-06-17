package com.example.wceeventmanager.bottomnav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wceeventmanager.R
import com.example.wceeventmanager.databinding.FragmentClubUserProfileBinding
import com.example.wceeventmanager.databinding.FragmentNormalUserProfileBinding


class ClubUserProfile : Fragment() {
    private var mbinding: FragmentClubUserProfileBinding?=null

    private val binding get()= mbinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mbinding= FragmentClubUserProfileBinding.inflate(inflater,container,false)



        return mbinding!!.root
    }

}