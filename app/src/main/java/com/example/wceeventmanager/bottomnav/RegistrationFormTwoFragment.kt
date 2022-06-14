package com.example.wceeventmanager

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.wceeventmanager.databinding.FragmentRegistrationForm2Binding



class RegistrationFormTwoFragment : Fragment() {

    private var mbinding: FragmentRegistrationForm2Binding?=null

    private val binding get()= mbinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mbinding= FragmentRegistrationForm2Binding.inflate(inflater,container,false)

        // Tags array - Area of Interest + Branches
        val tagsTopic = ArrayList<String>()
        val tagsBranch = ArrayList<String>()
        var allBranch = false

        val addAOI = binding?.addAoi
        val meetLink = binding?.EditTextMeetLink
        val venue = binding?.EditTextVenue
        val nextBtn = binding?.fabNext

        val lengthHandlerToast = layoutInflater.inflate(R.layout.custom_toast_tag_length, null)
        val duplicateHandlerToast = layoutInflater.inflate(R.layout.custom_toast_duplicate_tag, null)
        val emptyHandlerToast = layoutInflater.inflate(R.layout.custom_toast_empty_tag, null)
        if(binding?.rbonline?.isChecked == true){
            binding?.EditTextMeetLink?.isEnabled = true
        }
        nextBtn?.setOnClickListener {

        }

        addAOI?.setOnClickListener(View.OnClickListener {
           if(tagsTopic.size > 1)
           {
               Toast(requireContext()).apply {
                   duration = Toast.LENGTH_SHORT
                   view = lengthHandlerToast
               }.show()

               binding?.EditTextAOI?.text?.clear()
           }else{
               val aoi = binding?.EditTextAOI?.text.toString()
//                Toast.makeText(requireContext(), aoi, Toast.LENGTH_SHORT).show()

               // Check if the AOI is already in the list
                if(tagsTopic.contains(aoi))
                {
                     Toast(requireContext()).apply {
                          duration = Toast.LENGTH_SHORT
                          view = duplicateHandlerToast
                     }.show()

                    binding?.EditTextAOI?.text?.clear()
                }
               // Empty check
               else if(TextUtils.isEmpty(binding?.EditTextAOI?.text.toString())){
                    Toast(requireContext()).apply {
                        duration = Toast.LENGTH_SHORT
                        view = emptyHandlerToast
                    }.show()
               }

                else{
                    // trim and add
                    aoi.trim()
                    tagsTopic.add(aoi)
                    binding?.EditTextAOI?.text?.clear()
                }
           }
        })

        val civil_cb = binding?.cbCivil
        val mech_cb = binding?.cbmech
        val ece_cb = binding?.cbElectronics
        val ele_cb = binding?.cbElectrical
        val cse_cb = binding?.cbCSE
        val it_cb = binding?.cbIT

        if(civil_cb?.isChecked == true)
        {
            tagsBranch.add("Civil")
        }

        if(mech_cb?.isChecked == true)
        {
            tagsBranch.add("Mechanical")
        }

        if(ece_cb?.isChecked == true)
        {
            tagsBranch.add("Electronics")
        }

        if(ele_cb?.isChecked == true)
        {
            tagsBranch.add("Electrical")
        }

        if(cse_cb?.isChecked == true)
        {
            tagsBranch.add("CSE")
        }

        if(it_cb?.isChecked == true)
        {
            tagsBranch.add("IT")
        }

        if(tagsBranch.size == 6) allBranch = true


        // Back Button - Back to Registration Form 1
        val backBtn = binding?.backForm2
        backBtn?.setOnClickListener(View.OnClickListener {
            val fragment = RegistrationFormOneFragment()
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_fragment, fragment).commit()
        })


        return mbinding!!.root
    }


}