package com.example.itribez_android.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.itribez_android.Models.Post
import com.example.itribez_android.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class ProfileFragment : Fragment() {
    private lateinit var profileId: String
    lateinit var setting: ImageView
    var postList:List<Post>?=null
    private lateinit var profileImageView: ImageView
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        setting = view.findViewById(R.id.options_view)
        setting.setOnClickListener {
            val dialog = context?.let { it1 -> BottomSheetDialog(it1) }
            val viewBottomSheet = layoutInflater.inflate(R.layout.bottomsheet_settings,null)
            dialog?.setCancelable(true)
            dialog?.setCanceledOnTouchOutside(true)
            dialog?.setContentView(viewBottomSheet)
            dialog?.show()

            val editProfileBtn: Button = view.findViewById(R.id.edit_profile_Button)
            editProfileBtn.setOnClickListener {
                val editProfileFragment = EditProfileFragment()
                editProfileFragment.arguments = Bundle()
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.profile_fragment, editProfileFragment)
                    ?.addToBackStack(null)
                    ?.commit()
            }

            val toolbarUserNameTextView : TextView = view.findViewById(R.id.profile_toolbar_username)
            val profileUserNameTextView :  TextView = view.findViewById(R.id.username_in_profile)
            val profileFullNameTextView : TextView = view.findViewById(R.id.fullname_in_profile)
            val profileBioTextView :  TextView = view.findViewById(R.id.bio_profile)

            val args = this.arguments
            val toolbarUserName = args?.get("username")
            val profileFullName = args?.get("fullname")
            val profileUserName = args?.get("username")
            val profileBio = args?.get("bio")

            toolbarUserNameTextView.text = toolbarUserName.toString()
            profileUserNameTextView.text = profileUserName.toString()
            profileFullNameTextView.text = profileFullName.toString()
            profileBioTextView.text = profileBio.toString()

        }

        profileImageView = view.findViewById(R.id.profile_image_profile)
        sharedViewModel.selectedImage.observe(viewLifecycleOwner, { imageUri ->
            profileImageView.setImageURI(imageUri)
        })

        return view
    }


}