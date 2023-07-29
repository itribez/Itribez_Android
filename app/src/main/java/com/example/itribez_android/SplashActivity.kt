package com.example.itribez_android

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.widget.AppCompatImageView
import com.example.itribez_android.utils.SessionManager

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DURATION: Long = 2000 // 2 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val splashImage = findViewById<AppCompatImageView>(R.id.splash_logo)
        val fadeInAnimator = ObjectAnimator.ofFloat(splashImage, "alpha", 0f, 1f)
        fadeInAnimator.duration = SPLASH_DURATION
        fadeInAnimator.interpolator = AccelerateDecelerateInterpolator()
        fadeInAnimator.start()

        fadeInAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                val isLogin = SessionManager.getBool(applicationContext, SessionManager.IS_LOGIN)
                // Animation complete, navigate to the main activity
                if (isLogin) {
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
    }
}