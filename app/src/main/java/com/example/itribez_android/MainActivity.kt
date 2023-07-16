package com.example.itribez_android

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.itribez_android.Fragments.HomeFragment
import com.example.itribez_android.Fragments.ProfileFragment
import com.example.itribez_android.Fragments.beforepost
import com.example.itribez_android.Fragments.NotificationFragment
import com.example.itribez_android.Fragments.createpost
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            loadFragment(beforepost())
            // Permission granted, proceed with loading the image
        } else {
            // Permission denied, handle accordingly
        }
    }

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
                    loadFragment(createpost())
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

                else -> {
                    loadFragment(HomeFragment())
                    true
                }

            }
        }
    }
    private fun requestStoragePermission() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            loadFragment(beforepost())
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                // Explain why the permission is needed (optional)
                // You can show a dialog or a Snackbar here

                // Request the permission
                requestPermissionLauncher.launch(permission)
            } else {
                // Request the permission directly
                requestPermissionLauncher.launch(permission)
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