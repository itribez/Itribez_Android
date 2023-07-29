package com.example.itribez_android.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.itribez_android.Models.Post
import com.example.itribez_android.Models.User
import com.example.itribez_android.R
import kotlinx.android.synthetic.main.fragment_profile.view.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {
    private lateinit var profileId: String
    lateinit var setting:ImageView
    private lateinit var about: TextView
    private lateinit var soundandvibration: TextView
    private lateinit var notification: TextView
    private lateinit var helpandsupport: TextView

    var postList:List<Post>?=null
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        setting = view.findViewById(R.id.option_view)



        // Set click listener for the About TextView


        setting.setOnClickListener {
            val dialog = context?.let { it1 -> BottomSheetDialog(it1) }
            val viewBottomSheet = layoutInflater.inflate(R.layout.bottomsheet_settings,null)

            about = viewBottomSheet.findViewById(R.id.txtViewAbout)
            soundandvibration = viewBottomSheet.findViewById(R.id.txtViewSoundVibration)
            notification = viewBottomSheet.findViewById(R.id.txtViewNotificationSetting)
            helpandsupport = viewBottomSheet.findViewById(R.id.txtViewHelpSupport)

            dialog?.setCancelable(true)
            dialog?.setCanceledOnTouchOutside(true)
            dialog?.setContentView(viewBottomSheet)
            dialog?.show()

            //Log.d(TAG, "onCreateView: Hello")
            about.setOnClickListener {
                // Open the AboutFragment
                val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
                val aboutFragment = AboutFragment()
                fragmentManager.beginTransaction()
                    .replace(R.id.placeHolder, aboutFragment) // Replace fragment_container with the ID of the container layout in your activity
                    .addToBackStack(null)
                    .commit()
                dialog?.dismiss()

            }

            soundandvibration.setOnClickListener {
                // Open the AboutFragment
                val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
                val soundAndVibration = Sound_And_Vibration()
                fragmentManager.beginTransaction()
                    .replace(R.id.placeHolder, soundAndVibration) // Replace fragment_container with the ID of the container layout in your activity
                    .addToBackStack(null)
                    .commit()
                dialog?.dismiss()

            }

            notification.setOnClickListener {
                // Open the AboutFragment
                val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
                val notification = SettingNotification()
                fragmentManager.beginTransaction()
                    .replace(R.id.placeHolder, notification) // Replace fragment_container with the ID of the container layout in your activity
                    .addToBackStack(null)
                    .commit()
                dialog?.dismiss()

            }

            helpandsupport.setOnClickListener {
                // Open the AboutFragment
                val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
                val helpandsupport = HelpAndSupportFragment()
                fragmentManager.beginTransaction()
                    .replace(R.id.placeHolder, helpandsupport) // Replace fragment_container with the ID of the container layout in your activity
                    .addToBackStack(null)
                    .commit()
                dialog?.dismiss()

            }

        }





//        getUserInfo(view)
        return view
    }


    private fun getUserInfo(view: View) {
        val usersRef = FirebaseDatabase.getInstance().reference.child("Users").child(profileId)
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    val user = snapshot.getValue<User>(User::class.java)
                    Picasso.get().load(user!!.getImage()).placeholder(R.drawable.profile).into(view.profile_image_profile)
                    view.profile_toolbar_username?.text=user.getUsername()
                    view.fullname_in_profile?.text= user.getFullname()
                    view.username_in_profile?.text= user.getUsername()
                    view.bio_profile?.text= user.getBio()

                }
            }
        })
    }
}