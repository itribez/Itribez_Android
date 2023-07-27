package com.example.itribez_android.Fragments

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.itribez_android.R

class EditProfileFragment : Fragment() {

    private lateinit var imageView: ImageView
    private val CAMERA_REQUEST_CODE = 1
    private val GALLERY_REQUEST_CODE = 2
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val genderOptions = arrayOf("Male", "Female", "Other")
    private lateinit var gender: Spinner
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        val saveButton: Button = view.findViewById(R.id.btnsave)
        imageView=view.findViewById(R.id.upload_image)
        saveButton.setOnClickListener {
            val nameEditText: EditText = view.findViewById(R.id.name)
            val usernameEditText : EditText = view.findViewById(R.id.username)
            val birthdateEditText: EditText = view.findViewById(R.id.birthdate)
            val gender: Spinner = view.findViewById(R.id.genderspinner)
            val bioEditText: EditText = view.findViewById(R.id.edbio)
            val cityEditText: EditText = view.findViewById(R.id.edcity)
            val uploadPictureButton : Button = view.findViewById(R.id.btnuploadpic)
         //   sharedViewModel.setSelectedImage(imageView.Uri)

            uploadPictureButton.setOnClickListener { v->
                showPopUpMenu(v)
            }

           // setHasOptionsMenu(true)

            val name = nameEditText.text.toString()
            val username = usernameEditText.text.toString()
            val birthdate = birthdateEditText.text.toString()
            val bio = bioEditText.text.toString()
            val city = cityEditText.text.toString()

            val bundle = Bundle()
            bundle.putString("fullname",name)
            bundle.putString("username",username)
            bundle.putString("bio",bio)
            val profileFragment = ProfileFragment()
            profileFragment.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.profile_fragment,profileFragment)?.commit()

        }

        return view
    }

    fun showPopUpMenu(view: View){
        val popupMenu = PopupMenu(requireContext(),view)
        popupMenu.inflate(R.menu.popup_menu)
        imageView = view.findViewById(R.id.upload_image)

        popupMenu.setOnMenuItemClickListener {item : MenuItem -> when (item.itemId){
            R.id.open_camera -> {
                openCamera()
                true
            }
            R.id.open_gallary ->{
                openGallery()
                true
            }
            R.id.cancel -> {
                popupMenu.dismiss()
                true
            }
            else -> false
        }
        }

        popupMenu.show()
    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
            }
        }
    }

    private fun openGallery() {
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).also {
            startActivityForResult(it, GALLERY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    val imageUri = data?.data
                    imageView.setImageURI(imageUri)
                    sharedViewModel.setSelectedImage(imageUri!!)
                }

                GALLERY_REQUEST_CODE -> {
                    val imageUri = data?.data
                    imageView.setImageURI(imageUri)
                    sharedViewModel.setSelectedImage(imageUri!!)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gender.adapter = adapter

        // Handle item selection (optional)
        gender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View  ?,
                position: Int,
                id: Long
            ) {
                val selectedGender = genderOptions[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }
}