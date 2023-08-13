package com.example.itribez_android

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatImageView
import androidx.viewpager.widget.ViewPager
import com.example.itribez_android.utils.SessionManager
import kotlinx.android.synthetic.main.activity_splash.indicator
import android.view.MotionEvent

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DURATION: Long = 5000 // 5 seconds
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val btnGetStarted = findViewById<Button>(R.id.btnGetStarted)


        val splashImage = findViewById<AppCompatImageView>(R.id.splash_logo)
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val indicatorDots = findViewById<LinearLayout>(R.id.indicatorDots)

        val fadeInAnimator = ObjectAnimator.ofFloat(splashImage, "alpha", 0f, 1f)
        fadeInAnimator.duration = SPLASH_DURATION
        fadeInAnimator.interpolator = AccelerateDecelerateInterpolator()
        fadeInAnimator.start()

        gestureDetector = GestureDetector(this, SwipeGestureListener())



        btnGetStarted.setOnClickListener {

                 // navigate to the login activity
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
        }





        fadeInAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                val isLogin = SessionManager.getBool(applicationContext, SessionManager.IS_LOGIN)
                // Animation complete, navigate to the main activity
                if (isLogin) {
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }

//                else {
//                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//

            }
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    inner class SwipeGestureListener : GestureDetector.SimpleOnGestureListener() {
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val diffX = e2?.x?.minus(e1?.x ?: 0F) ?: 0F
            val diffY = e2?.y?.minus(e1?.y ?: 0F) ?: 0F

            if (Math.abs(diffX) > Math.abs(diffY)
                && Math.abs(diffX) > SWIPE_THRESHOLD
                && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD
            ) {
                // Swipe detected, navigate to the desired page
                val intent = Intent(this@SplashActivity, onboarding_page1::class.java)
                startActivity(intent)
                finish()
                return true
            }
            return false
        }
    }
}