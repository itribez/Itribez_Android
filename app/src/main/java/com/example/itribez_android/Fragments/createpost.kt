package com.example.itribez_android.Fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.itribez_android.R
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File
import kotlin.math.log

class createpost : Fragment() {

//    private lateinit var imageView: ImageView
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_createpost, container, false)
//
//        // Initialize the ImageView
//        imageView = view.findViewById(R.id.img)
//
//        // Retrieve the selected image URI from arguments
////        val selectedImageUriString = arguments?.getString("selectedImageUri").toString()
////        if (selectedImageUriString != null) {
////            Log.i("image",selectedImageUriString)
////        }
////        val uri = Uri.fromFile(File(selectedImageUriString))
//        // Load the image using Glide or any other image loading library
////        Glide.with(this)
////            .load(uri.path)
////
////            .into(imageView)
//        val imgPath = arguments?.getString("selectedImageUri")
//        val file = File(imgPath)
//        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
//        imageView.setImageBitmap(bitmap)
//
////        Glide.with(context)
////            .load(pictureUri.getPath())
////            .transform(new CircleTransform(..))
////        .into(profileAvatar);
//
//        // Rest of your code
//
//        return view
//    }
// Declare your EditText variables
    private lateinit var descriptionEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var tagEditText: EditText
    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_createpost, container, false)

        // Initialize your EditText fields
        descriptionEditText = view.findViewById(R.id.description)
        locationEditText = view.findViewById(R.id.location)
        tagEditText = view.findViewById(R.id.Tag)
        imageView = view.findViewById(R.id.img)

        // Retrieve the selected image URI from arguments
//        val selectedImageUriString = arguments?.getString("selectedImageUri").toString()
//        val uri = Uri.fromFile(File(selectedImageUriString))
//        Log.d("uri is ", "Tag: $selectedImageUriString")
//        // Load the image using Glide or any other image loading library
//        Glide.with(this)
//            .load(uri)
//            .into(imageView)


        val selectedImageUriString = arguments?.getString("selectedImageUri")
        val file = File(selectedImageUriString)
        Log.d("uri is ", "Tag: $selectedImageUriString")
        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
        imageView.setImageBitmap(bitmap)


        // Find the "Post" button
        val postButton: Button = view.findViewById(R.id.postbutton)

        // Set the OnClickListener to the "Post" button
        postButton.setOnClickListener {
            // Retrieve the text from EditText fields
            val description = descriptionEditText.text.toString()
            val location = locationEditText.text.toString()
            val tag = tagEditText.text.toString()

            // Log the text in the logcat
            Log.d("CreatePostFragment", "Description: $description")
            Log.d("CreatePostFragment", "Location: $location")
            Log.d("CreatePostFragment", "Tag: $tag")
        }

        return view
    }

}