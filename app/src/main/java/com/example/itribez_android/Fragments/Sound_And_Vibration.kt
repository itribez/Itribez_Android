package com.example.itribez_android.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.example.itribez_android.R



class Sound_And_Vibration : Fragment() {

    private lateinit var soundSeekBar: SeekBar
    private lateinit var vibrationSeekBar: SeekBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sound_and_vibration, container, false)


        val backbutton: ImageView = view.findViewById(R.id.back)
        backbutton.setOnClickListener {
            // Replace this with the Fragment you want to navigate to
            parentFragmentManager.beginTransaction()
                .replace(R.id.placeHolder, ProfileFragment())
                .addToBackStack(null)
                .commit()
        }

        soundSeekBar = view.findViewById(R.id.soundSeekBar)
        vibrationSeekBar = view.findViewById(R.id.vibrationSeekBar)

        soundSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // TODO: Implement sound level control logic
                val soundLevel = progress

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        vibrationSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // TODO: Implement vibration level control logic
                val vibrationLevel = progress

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        return view
    }



}