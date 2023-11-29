//package com.example.itribez_android
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.GestureDetector
//import android.view.MotionEvent
//import android.widget.Button
//
//class onboarding_page2 : AppCompatActivity() {
//
//    private lateinit var gestureDetector: GestureDetector
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_onboarding_page2)
//
//        val btnGetStarted = findViewById<Button>(R.id.btnGetStarted)
//
//        gestureDetector = GestureDetector(this, SwipeGestureListener())
//
//        btnGetStarted.setOnClickListener {
//
//            // navigate to the login activity
//            val intent = Intent(this@onboarding_page2, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//
//    }
//
//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        gestureDetector.onTouchEvent(event)
//        return super.onTouchEvent(event)
//    }
//
//    inner class SwipeGestureListener : GestureDetector.SimpleOnGestureListener() {
//        private val SWIPE_THRESHOLD = 100
//        private val SWIPE_VELOCITY_THRESHOLD = 100
//
//        override fun onFling(
//            e1: MotionEvent,
//            e2: MotionEvent,
//            velocityX: Float,
//            velocityY: Float
//        ): Boolean {
//            val diffX = e2?.x?.minus(e1?.x ?: 0F) ?: 0F
//            val diffY = e2?.y?.minus(e1?.y ?: 0F) ?: 0F
//
//            if (Math.abs(diffX) > Math.abs(diffY)
//                && Math.abs(diffX) > SWIPE_THRESHOLD
//                && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD
//            ) {
//                if (diffX > 0) {
//                    // Right swipe, navigate to the next onboarding page
//                    val intent = Intent(this@onboarding_page2, onboarding_page1::class.java)
//                    startActivity(intent)
//                    finish()
//                    return true
//                } else {
//                    // Left swipe, navigate back to the SplashActivity
//                    val intent = Intent(this@onboarding_page2, LoginActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                    return true
//                }
//            }
//            return false
//        }
//    }
//
//}