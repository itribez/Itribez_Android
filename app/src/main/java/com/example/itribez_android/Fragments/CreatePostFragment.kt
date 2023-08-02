package com.example.itribez_android.Fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.itribez_android.R

class CreatePostFragment : Fragment() {


    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_GALLERY_CREATE_POST = 2
    private lateinit var imageView: ImageView
    private var selectedOption: Int = -1
    private lateinit var descriptionEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var tagEditText: EditText
    private lateinit var imageView1: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_createpost, container, false)

        descriptionEditText = view.findViewById(R.id.description)
        locationEditText = view.findViewById(R.id.location)
        tagEditText = view.findViewById(R.id.Tag)

        imageView = view.findViewById(R.id.img)
        imageView1 = view.findViewById(R.id.back)
        imageView.setOnClickListener {
            showImageSourceDialog()
        }

        val postButton: Button = view.findViewById(R.id.postbutton)
        postButton.setOnClickListener {
            // Retrieve the text from EditText fields
            val description = descriptionEditText.text.toString()
            val location = locationEditText.text.toString()
            val tag = tagEditText.text.toString()

            // Log the text in the logcat
            Log.d("CreatePostFragment", "Description: $description")
            Log.d("CreatePostFragment", "Location: $location")
            Log.d("CreatePostFragment", "Tag: $tag")

            Toast.makeText(requireContext(), "Post created successfully", Toast.LENGTH_SHORT).show()

            // Replace this with the Fragment you want to navigate to (e.g., MainFragment)
            parentFragmentManager.beginTransaction()
                .replace(R.id.placeHolder, HomeFragment())
                .addToBackStack(null)
                .commit()



        }

        imageView1.setOnClickListener {
            // Replace this with the Fragment you want to navigate to
            parentFragmentManager.beginTransaction()
                .replace(R.id.placeHolder, HomeFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
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
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_IMAGE_CAPTURE

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
            requestPermissions(
//                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_GALLERY_CREATE_POST
            )

        } else{
            openGallery()
            Log.d("CreatePostFragment", "Here is code1")
        }

    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            Log.d("CreatePostFragment", "Here is camera2")
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_GALLERY_CREATE_POST)
        Log.d("CreatePostFragment", "Here is gallery opoen2 ")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    imageView.setImageBitmap(imageBitmap)
                }
                REQUEST_GALLERY_CREATE_POST -> {
                    val selectedImageUri = data?.data
                    Glide.with(this)
                        .load(selectedImageUri)
                        .into(imageView)
                    Log.d("CreatePostFragment", "Here is gallery3")
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
            REQUEST_IMAGE_CAPTURE -> {
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
            REQUEST_GALLERY_CREATE_POST -> {
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
}