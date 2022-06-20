package com.example.wceeventmanager.bottomnav

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.media.MediaBrowserServiceCompat.RESULT_OK
import com.bumptech.glide.Glide
import com.example.wceeventmanager.R
import com.example.wceeventmanager.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {
    var displayImage : ImageView? = null
    var img_uri : String? = null

    // Binding
    private lateinit var mbinding: FragmentEditProfileBinding
    private val binding get() = mbinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        mbinding = FragmentEditProfileBinding.bind(view)

        val animation = AnimationUtils.loadAnimation(context, com.example.wceeventmanager.R.anim.move)
        binding.nametv.startAnimation(animation)

        displayImage = binding.uploadImage

        val imgUploadBTN = binding.imgUploadBtn
        imgUploadBTN.setOnClickListener(){ uploadImageFromGallery() }

        val saveBTN = binding.editProfileSaveBtn
        saveBTN.setOnClickListener(){ saveProfile() }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun saveProfile() {
        val new_name = binding.editProfileNameEt.text.toString()
        val new_email = binding.editProfileEmailEt.text.toString()
        val new_profile_pic = img_uri


        val fragment = ProfileFragment()
        val bundle = Bundle()
        bundle.putString("name", new_name)
        bundle.putString("email", new_email)
        bundle.putString("profile_pic", new_profile_pic)
        fragment.arguments = bundle

        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun uploadImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            val image_uri = data.data
            img_uri = image_uri.toString()

            Glide.with(this)
                .load(image_uri)
                .fitCenter()
                .circleCrop()
                .placeholder(R.drawable.ic_baseline_image_24)
                .into(displayImage!!)
        }
    }
}