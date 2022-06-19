package com.example.wceeventmanager.bottomnav

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.wceeventmanager.FragmentViewModel
import com.example.wceeventmanager.R
import com.example.wceeventmanager.databinding.FragmentProfileBinding
import io.getstream.avatarview.coil.loadImage

class ProfileFragment : Fragment() {

    private  var mbinding:FragmentProfileBinding? = null
    private val binding get() = mbinding!!

   // private val viewModel :FragmentViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       mbinding =  FragmentProfileBinding.inflate(inflater,container,false)

        val user_name = binding.nametv
        val user_email = binding.emailtv
        val user_image = binding.profilepic

        val editProfileRL = binding.editprofileRelativeLayout
        val applyForClubUserRL = binding.applyForClubUserRelativeLayout
        val viewEventsRL = binding.viewEventsRelativeLayout
        val delClubRL = binding.delClubUserRelativeLayout
        val eventRequestRL = binding.eventRequestRelativeLayout
        val clubRequestRL = binding.clubRequestRelativeLayout
        val logoutRL = binding.logoutRelativeLayout

//        val bundle = arguments

       // Object of Shared Preference to access the data from the shared preference
       val sharedPref = activity?.getSharedPreferences("userInfo", Context.MODE_PRIVATE)

       val user = sharedPref?.getString("name", "null")
       val email = sharedPref?.getString("email", "null")
       val isVerified = sharedPref?.getBoolean("isVerified", false)
       val isAdmin = sharedPref?.getBoolean("isAdmin", false)
       val isClubUser = sharedPref?.getBoolean("isClubUser", false)

       user_name.text = user
       user_email.text = email

       Toast.makeText(requireContext(), "$isVerified $isAdmin $isClubUser", Toast.LENGTH_SHORT).show()

       if(isVerified == true){
            viewEventsRL.visibility = View.GONE
            delClubRL.visibility = View.GONE
            eventRequestRL.visibility = View.GONE
            clubRequestRL.visibility = View.GONE
       }
       else if(isClubUser == true){
            applyForClubUserRL.visibility = View.GONE
            eventRequestRL.visibility = View.GONE
            clubRequestRL.visibility = View.GONE
       }
       else if(isAdmin == true){
            applyForClubUserRL.visibility = View.GONE
            delClubRL.visibility = View.GONE
       }

       return mbinding?.root
    }


}