package com.example.itribez_android.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.itribez_android.Api.Responses.BaseResponse
import com.example.itribez_android.R
import kotlinx.android.synthetic.main.fragment_help_and_support.btnSubmit
import kotlinx.android.synthetic.main.fragment_help_and_support.etEmail
import kotlinx.android.synthetic.main.fragment_help_and_support.etName
import kotlinx.android.synthetic.main.fragment_help_and_support.etPhoneNumber
import kotlinx.android.synthetic.main.fragment_help_and_support.etProblem

class HelpAndSupportFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_help_and_support, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val backbutton: ImageView = view.findViewById(R.id.back)
        backbutton.setOnClickListener {
            // Replace this with the Fragment you want to navigate to
            parentFragmentManager.beginTransaction()
                .replace(R.id.placeHolder, ProfileFragment())
                .addToBackStack(null)
                .commit()
        }

        btnSubmit.setOnClickListener {
            val name = etName.text.toString()
            val phoneNumber = etPhoneNumber.text.toString()
            val email = etEmail.text.toString()
            val problem = etProblem.text.toString()
            Toast.makeText(requireContext(), "Help Requested Successfully", Toast.LENGTH_SHORT).show()
            Log.d("HelpAndSupportFragment", "Name: $name")
            Log.d("HelpAndSupportFragment", "Phone Number: $phoneNumber")
            Log.d("HelpAndSupportFragment", "Email: $email")
            Log.d("HelpAndSupportFragment", "Problem: $problem")
        }
    }

}