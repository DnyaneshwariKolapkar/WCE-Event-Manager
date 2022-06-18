package com.example.wceeventmanager.bottomnav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wceeventmanager.R
import com.example.wceeventmanager.databinding.FragmentAdminUserProfileBinding
import com.example.wceeventmanager.databinding.FragmentClubUserProfileBinding
//import io.getstream.avatarview.coil.loadImage


class AdminUserProfile : Fragment() {
    private var mbinding: FragmentAdminUserProfileBinding?=null

    private val binding get()= mbinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mbinding= FragmentAdminUserProfileBinding.inflate(inflater,container,false)

       // mbinding!!.avatarView2.loadImage(R.drawable.man)


        return mbinding!!.root
    }
}