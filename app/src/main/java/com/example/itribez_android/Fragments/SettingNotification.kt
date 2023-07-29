package com.example.itribez_android.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.itribez_android.R
import kotlinx.android.synthetic.main.fragment_setting_notification.*

class SettingNotification : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting_notification, container, false)
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

        switchMessageNotification.setOnCheckedChangeListener { _, isChecked ->
            // Implement message notification logic here
        }

        switchGroupNotification.setOnCheckedChangeListener { _, isChecked ->
            // Implement group notification logic here
        }

        switchEmailNotification.setOnCheckedChangeListener { _, isChecked ->
            // Implement email notification logic here
        }

        radioGroupSoundOptions.setOnCheckedChangeListener { _, checkedId ->
            // Implement sound options logic here
            when (checkedId) {
                R.id.radioButtonDefault -> {
                    // Handle default sound option selection
                }
                R.id.radioButtonSelected -> {
                    // Handle selected sound option selection
                    // Open local file to choose a sound
                }
            }
        }
    }

}