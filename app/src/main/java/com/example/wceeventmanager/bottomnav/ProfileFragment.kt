package com.example.wceeventmanager.bottomnav

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import com.example.wceeventmanager.FragmentViewModel
import com.example.wceeventmanager.R
import com.example.wceeventmanager.databinding.FragmentProfileBinding
import de.hdodenhof.circleimageview.CircleImageView
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
        val user_image = binding.profileImage

        val editProfileRL = binding.editprofileRelativeLayout
        val applyForClubUserRL = binding.applyForClubUserRelativeLayout
        val viewEventsRL = binding.viewEventsRelativeLayout
        val delClubRL = binding.delClubUserRelativeLayout
        val eventRequestRL = binding.eventRequestRelativeLayout
        val clubRequestRL = binding.clubRequestRelativeLayout
        val logoutRL = binding.logoutRelativeLayout

       // Object of Shared Preference to access the data from the shared preference
       val sharedPref = activity?.getSharedPreferences("userInfo", Context.MODE_PRIVATE)

       val user = sharedPref?.getString("name", "null")
       val email = sharedPref?.getString("email", "null")
       val isVerified = sharedPref?.getBoolean("isVerified", false)
       val isAdmin = sharedPref?.getBoolean("isAdmin", false)
       val isClubUser = sharedPref?.getBoolean("isClubUser", false)

       user_name.text = user
       user_email.text = email

       //Toast.makeText(requireContext(), "$isVerified $isAdmin $isClubUser", Toast.LENGTH_SHORT).show()

       if(isVerified == true){
           editProfileRL.visibility = View.VISIBLE
           applyForClubUserRL.visibility = View.VISIBLE
           logoutRL.visibility = View.VISIBLE

           viewEventsRL.visibility = View.GONE
           delClubRL.visibility = View.GONE
           eventRequestRL.visibility = View.GONE
           clubRequestRL.visibility = View.GONE
       }

       if(isClubUser == true){
           editProfileRL.visibility = View.VISIBLE
           viewEventsRL.visibility = View.VISIBLE
           delClubRL.visibility = View.VISIBLE
           logoutRL.visibility = View.VISIBLE

           applyForClubUserRL.visibility = View.GONE
           eventRequestRL.visibility = View.GONE
           clubRequestRL.visibility = View.GONE
       }

       if(isAdmin == true){
           editProfileRL.visibility = View.VISIBLE
           viewEventsRL.visibility = View.VISIBLE
           eventRequestRL.visibility = View.VISIBLE
           clubRequestRL.visibility = View.VISIBLE
           logoutRL.visibility = View.VISIBLE

           applyForClubUserRL.visibility = View.GONE
           delClubRL.visibility = View.GONE
       }

       editProfileRL.setOnClickListener(){
              val fragment = EditProfileFragment()
              val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(com.example.wceeventmanager.R.id.main_fragment, fragment)
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.commit()
       }

        val bundle = arguments
        if(bundle != null){

            if(bundle.getString("profile_pic") != null){
                val imgPath = bundle.getString("profile_pic")!!.toUri()
                val img = BitmapFactory.decodeFile(imgPath.toString())
                user_image.setImageURI(imgPath)

                Toast.makeText(requireContext(), "Image Loaded", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(requireContext(), "Image not Loaded", Toast.LENGTH_SHORT).show()
            }

            if(bundle.getString("name") != null){
                val new_name = bundle.getString("name")
                user_name.text = new_name
            }
            else{
                user_name.text = user
            }

            if(bundle.getString("email") != null){
                val new_email = bundle.getString("email")
                user_email.text = new_email
            }
            else{
                user_email.text = email
            }
        }

       return mbinding?.root
    }


}

