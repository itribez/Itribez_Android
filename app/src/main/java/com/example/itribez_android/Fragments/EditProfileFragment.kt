package com.example.itribez_android.Fragments


import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment

import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Gallery
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import android.app.DatePickerDialog
import android.net.Uri
import com.example.itribez_android.R
import java.util.Calendar

class EditProfileFragment : Fragment() {



    private lateinit var upload_image: ImageView
    private val CAMERA_REQUEST_CODE = 103
    private val GALLERY_REQUEST_CODE = 102
    private var selectedOption: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        val saveButton: Button = view.findViewById(R.id.btnsave)
        upload_image = view.findViewById(R.id.upload_image)
        val calendarImageView : ImageView = view.findViewById(R.id.calendar)

        calendarImageView.setOnClickListener {
            openDatePicker()
        }

        saveButton.setOnClickListener {
            val nameEditText: EditText = view.findViewById(R.id.name)
            val usernameEditText : EditText = view.findViewById(R.id.username)
            val bioEditText: EditText = view.findViewById(R.id.edbio)
            val cityEditText: EditText = view.findViewById(R.id.edcity)
            val name = nameEditText.text.toString()
            val username = usernameEditText.text.toString()
            val bio = bioEditText.text.toString()
            val city = cityEditText.text.toString()
            val imageUri = upload_image.tag as String?


          val resultData = Bundle().apply {
              putString("fullname",name)
              putString("username",username)
              putString("bio",bio)
              putString("imageUri1",imageUri)
          }
            Toast.makeText(requireContext(), "Profile Successfully Edited", Toast.LENGTH_SHORT).show()

            parentFragmentManager.setFragmentResult("editProfileData",resultData)
            parentFragmentManager.popBackStack()

        }
        val uploadPictureButton : Button = view.findViewById(R.id.btnuploadpic)
        uploadPictureButton.setOnClickListener {
            showImageSourceDialog()
        }

        return view
    }

    private fun openDatePicker(){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val birthdateEditText: EditText = requireView().findViewById(R.id.birthdate)
                birthdateEditText.setText("$selectedYear-${selectedMonth + 1}-$selectedDay")
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
    private fun showImageSourceDialog() {
        val options = arrayOf<CharSequence>("Open Camera", "Choose from Gallery")

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select Image Source")
        builder.setItems(options) { _, item ->
            selectedOption = item
            when (item) {
                0 -> checkCameraPermissionAndOpenCamera()
                1 -> checkGalleryPermissionAndOpenGallery()
            }
        }
        builder.show()
    }

    private fun checkCameraPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE

            )

        } else {
            openCamera()
        }
    }

    private fun checkGalleryPermissionAndOpenGallery() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                GALLERY_REQUEST_CODE
            )

        } else{
            openGallery()
        }

    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE )
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    upload_image.setImageBitmap(imageBitmap)

                    val imageUri = saveImageToGallery(imageBitmap)
                    upload_image.tag = imageUri.toString()
                }
                GALLERY_REQUEST_CODE -> {
                    val selectedImageUri = data?.data
                    Glide.with(this)
                        .load(selectedImageUri)
                        .into(upload_image)

                    upload_image.tag = selectedImageUri.toString()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (selectedOption == 0) { // Camera option was selected
                        openCamera()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Camera permission denied",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            GALLERY_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (selectedOption == 1) { // Gallery option was selected
                        openGallery()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Gallery permission denied",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun saveImageToGallery(bitmap: Bitmap): Uri {
        val resolver = requireContext().contentResolver
        val imageFileName = "image_${System.currentTimeMillis()}.jpg"
        val imageUri = MediaStore.Images.Media.insertImage(resolver, bitmap, imageFileName, null)
        return Uri.parse(imageUri)
    }

}