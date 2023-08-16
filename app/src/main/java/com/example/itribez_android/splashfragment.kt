package com.example.itribez_android

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.widget.AppCompatImageView
import com.example.itribez_android.utils.SessionManager


class splashfragment : Fragment() {

    private val SPLASH_DURATION: Long = 15000
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_splashfragment, container, false)

        val splashImage = rootView.findViewById<AppCompatImageView>(R.id.splash_logo)

        val fadeInAnimator = ObjectAnimator.ofFloat(splashImage, "alpha", 0f, 1f)
        fadeInAnimator.duration = SPLASH_DURATION
        fadeInAnimator.interpolator = AccelerateDecelerateInterpolator()
        fadeInAnimator.start()


        return rootView
    }
}