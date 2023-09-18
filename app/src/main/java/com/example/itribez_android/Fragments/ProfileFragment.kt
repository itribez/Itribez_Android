package com.example.itribez_android.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
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
import java.util.*

class ProfileFragment : Fragment() {
    private lateinit var profileId: String
    lateinit var setting:ImageView
    var postList:List<Post>?=null
    private lateinit var firebaseUser: FirebaseUser
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        setting = view.findViewById(R.id.option_view)
        setting.setOnClickListener {
            val dialog = context?.let { it1 -> BottomSheetDialog(it1) }
            val viewBottomSheet = layoutInflater.inflate(R.layout.bottomsheet_settings,null)
            dialog?.setCancelable(true)
            dialog?.setCanceledOnTouchOutside(true)
            dialog?.setContentView(viewBottomSheet)
            dialog?.show()
            //Log.d(TAG, "onCreateView: Hello")
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