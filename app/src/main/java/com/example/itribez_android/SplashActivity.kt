package com.example.itribez_android

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.viewpager.widget.ViewPager
import com.example.itribez_android.utils.SessionManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val btnGetStarted = findViewById<Button>(R.id.btnGetStarted)


        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val adapter = OnboardingPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter

        val indicator = findViewById<ImageView>(R.id.indicator)
        viewPager.currentItem = 0
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // Calculate the new position of the indicator based on position and offset
                val indicatorX = (position + positionOffset) * indicator.width
                indicator.translationX = indicatorX
            }

            override fun onPageSelected(position: Int) {
                if (position == adapter.count - 1) {
                    OnboardingFragment()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

//        val fadeInAnimator = ObjectAnimator.ofFloat(splashImage, "alpha", 0f, 1f)
//        fadeInAnimator.duration = SPLASH_DURATION
//        fadeInAnimator.interpolator = AccelerateDecelerateInterpolator()
//        fadeInAnimator.start()



        btnGetStarted.setOnClickListener {

                 // navigate to the login activity
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
        }





//        fadeInAnimator.addListener(object : Animator.AnimatorListener {
//            override fun onAnimationStart(animation: Animator) {}
//            override fun onAnimationEnd(animation: Animator) {
//                val isLogin = SessionManager.getBool(applicationContext, SessionManager.IS_LOGIN)
//                // Animation complete, navigate to the main activity
//                if (isLogin) {
//                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//                else {
//                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//
            }

//            override fun onAnimationCancel(animation: Animator) {}
//            override fun onAnimationRepeat(animation: Animator) {}
//        })
//    }



}
class OnboardingPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        // Return the appropriate fragment based on the position
        return when (position) {
            0 -> splashfragment() // Replace with your actual fragment classes
            1 -> OnboardingFragment() // Replace with your actual fragment classes
            2 -> OnboardingFragment2() // Replace with your actual fragment classes
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    override fun getCount(): Int {
        // Return the total number of onboarding screens
        return 3 // Replace with the actual number of onboarding screens
    }
}