package com.example.itribez_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.itribez_android.Fragments.HomeFragment
import com.example.itribez_android.Fragments.ProfileFragment
import com.example.itribez_android.fragments.MessagesFragment
import com.example.itribez_android.fragments.NotificationFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(HomeFragment())
        bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    true
                }

                R.id.create -> {
                    loadFragment(MessagesFragment())
                    true
                }

                 R.id.notification -> {
                    loadFragment(NotificationFragment())
                    true
                }
                R.id.settings -> {
                    loadFragment(ProfileFragment())
                    true
                }

                else ->{
                    loadFragment(HomeFragment())
                    true
            }

            }
        }


    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.placeHolder, fragment)
        transaction.commit()
    }

    /*private fun openMessageFragment(messagesFragment: MessagesFragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.placeHolder,messagesFragment)
        fragmentTransaction.commit()
    }*/
}