package com.example.itribez_android.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.itribez_android.R


class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val aboutTextView: TextView = view.findViewById(R.id.aboutTextView)


        // Replace "Your Application Content" with the actual content of your application
        val applicationContent = "Here is our application Itribez with the beta version. If you need any help contact is available in contact us page."
        aboutTextView.text = applicationContent

        val backbutton: ImageView = view.findViewById(R.id.back)
        backbutton.setOnClickListener {
            // Replace this with the Fragment you want to navigate to
            parentFragmentManager.beginTransaction()
                .replace(R.id.placeHolder, ProfileFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}