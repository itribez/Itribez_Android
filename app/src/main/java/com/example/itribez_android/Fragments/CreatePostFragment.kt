package com.example.itribez_android.Fragments

import android.Manifest
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
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
//import com.cloudinary.android.MediaManager
//import com.cloudinary.android.callback.ErrorInfo
//import com.cloudinary.android.callback.UploadCallback
import com.example.itribez_android.Api.Requests.CreatePostRequest
import com.example.itribez_android.R
import com.example.itribez_android.utils.SessionManager
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.IOException

class CreatePostFragment : Fragment() {

    private val REQUEST_IMAGE_CAPTURE = 1
    private val Image_requestcode = 100
    private val REQUEST_GALLERY = 2
    private lateinit var imageView: ImageView
    private var selectedOption: Int = -1
    private lateinit var descriptionEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var tagEditText: EditText
    private lateinit var imageView1: ImageView
    private var selectedImageBase64: String? = null
    private var config: MutableMap<String, Any> = HashMap()
    private var imagePath: Uri? = null

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

//        val viewModel = ViewModelProvider(this).get(CreatePostViewModel::class.java)
//        viewModel.createPostResult.observe(viewLifecycleOwner) { response ->
//            when (response) {
//                is BaseResponse.Success -> {
//                    // Post created successfully
//                    Toast.makeText(requireContext(), "Post created successfully", Toast.LENGTH_SHORT).show()
//
//                    // Optionally, you can navigate to another fragment or perform other actions here.
//                }
//                is BaseResponse.Error -> {
//                    // Handle the error (e.g., show an error message)
//                    Toast.makeText(requireContext(), "Error not posted", Toast.LENGTH_SHORT).show()
//                }
//                is BaseResponse.Loading -> {
//                    // Handle loading state if needed (e.g., show a loading indicator)
//                }
//            }
//        }


//        initConfig()
        postButton.setOnClickListener {



            //Retrieve the text from EditText fields
            val description = descriptionEditText.text.toString()
            val location = locationEditText.text.toString()
            val tagsString = tagEditText.text.toString()
            // Convert the comma-separated string of tags to an ArrayList
            val tags = ArrayList(tagsString.split(",").map { it.trim() })

            val images = selectedImageBase64

            val jwtToken = SessionManager.getToken(requireContext())

            if (jwtToken != null) {
                val userId = SessionManager.getUserId(requireContext())
                val createPostRequest = CreatePostRequest(userId, location, description, tags
                )
                sendCreatePostRequest(createPostRequest, jwtToken)
            } else {
                // Handle the case when the token is null
                Log.e("ContentValues.TAG", "Token is null")
            }

//            lifecycleScope.launch {
//                val newPost = PostEntity(postID = post.postID ,description,location,tag)
//                postDao.insert(newPost)
//                val resultData= Bundle().apply{
//                    putString("description",description)
//                    putString("location",location)
//                    putString("tag",tag)
//                    putString("imageUriPost",imageUri)
//                }
//                Constant.arrayListPostMain.add(DataClassPosts(description,location,tag))
//
////                Toast.makeText(requireContext(), "Post Created Successfully", Toast.LENGTH_SHORT).show()
//                parentFragmentManager.setFragmentResult("Create Post",resultData)
//                parentFragmentManager.popBackStack()
//            }
//            viewModel.createPost(description, location, tag,selectedImageBase64)

            //Log the text in the logcat
            Log.d(TAG, "onCreateView: $description $location $tag")
//            MediaManager.get().upload(imagePath).callback(object : UploadCallback {
//                override fun onStart(requestId: String) {
//                    Log.d(TAG, "onStart: ")
//                }
//
//                override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {
//                    Log.d(TAG, "onStart: ")
//                }
//
//                override fun onSuccess(requestId: String, resultData: Map<*, *>) {
//                    Log.d(TAG, "onStart: ")
//                }
//
//                override fun onError(requestId: String, error: ErrorInfo) {
//                    Log.d(TAG, "onStart: ")
//                }
//
//                override fun onReschedule(requestId: String, error: ErrorInfo) {
//                    Log.d(TAG, "onStart: ")
//                }
//            }).dispatch()


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

//    private fun initConfig() {
//        config["cloud_name"] = "dzv1vpd2v"
//        config["api_key"] = "874536865334618"
//        config["api_secret"] = "zFa7GgUs0RTcXpOJryVgJXpi02U"
//        //        config.put("secure", true);
//        MediaManager.init(requireContext(), config)
//    }

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
                0
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
                1
            )
        } else {

            openGallery()
        }
    }

//    private fun openCamera() {
//        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        takePicture.launch(takePictureIntent)
//    }
//
//    private fun openGallery() {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        gallery.launch(intent)
//    }
//
//    private val takePicture = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        if (result.resultCode == Activity.RESULT_OK) {
//            val imageBitmap = result.data?.extras?.get("data") as Bitmap
//            imageView.setImageBitmap(imageBitmap)
//        }
//    }
//
//    private val gallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        if (result.resultCode == Activity.RESULT_OK) {
//            val selectedImageUri = result.data?.data
//            if (selectedImageUri != null) {
//                selectedImageBase64 = convertImageToBase64(selectedImageUri)
//                Glide.with(this)
//                    .load(selectedImageUri)
//                    .into(imageView)
//                Log.d("Selected image","$selectedImageUri")
//            }
//        }
//    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_GALLERY)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    imageView.setImageBitmap(imageBitmap)
                    // Convert the bitmap to a URI and assign it to the imagePath variable
                    val uri = getImageUri(requireContext(), imageBitmap)
                    imagePath = uri
                }
                REQUEST_GALLERY -> {
                    val selectedImageUri = data?.data
                    if (selectedImageUri != null) {
                        imagePath = selectedImageUri
                        Picasso.get().load(imagePath).into(imageView)
                    } else {
                        // Handle the case when selectedImageUri is null
                        Log.e(TAG, "Selected image URI is null")
                    }
                }
            }
        }
    }
    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }
    private fun sendCreatePostRequest(createPostRequest: CreatePostRequest,jwtToken: String) {

        // Use Dispatchers.IO for network operations in coroutines
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val client = OkHttpClient()

                val request = Request.Builder()
                    .url("https://itribez-node-apis.onrender.com/post/create")
                    .header("Authorization", "Bearer $jwtToken")
                    .post(
                        Gson().toJson(createPostRequest).toRequestBody("application/json".toMediaTypeOrNull())
                    )
                    .build()

                val response: Response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    activity?.runOnUiThread {
                        Log.d(TAG, "Response successful: $responseBody $jwtToken")
//                        uploadImageToCloudinary(selectedImageBase64)
                        // Handle success here
                    }
                } else {
                    activity?.runOnUiThread {
                        Log.d(TAG, "Request failed: ${response.code}")
                        // Handle failure here
                    }
                }
            } catch (e: IOException) {
                activity?.runOnUiThread {
                    Log.e(TAG, "Network error: ${e.message}")
                    // Handle network error here
                }
            }
        }
    }

//    private fun uploadImageToCloudinary(imageBase64: String?) {
//        // Use the Cloudinary SDK to upload the image
//        // Replace 'cloud_name', 'api_key', and 'api_secret' with your Cloudinary credentials
//        val config = HashMap<String, String>()
//        config["cloud_name"] = "dahhibj3z"
//        config["api_key"] = "764736966434972"
//        config["api_secret"] = "JuPiRKn7NdmlvG5RPrWRcIuyaxE"
//
//        val cloudinary = Cloudinary(config)
//
//        // Perform the upload operation
//        val response = cloudinary.uploader().upload(imageBase64, ObjectUtils.emptyMap())
//
//        // Handle the response from Cloudinary
//        Log.d(TAG, "Cloudinary response: $response")
//    }

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
            REQUEST_GALLERY -> {
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