import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.example.itribez_android.Api.Requests.CreatePostRequest
import com.example.itribez_android.R
import com.example.itribez_android.utils.SessionManager
import com.google.gson.Gson
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.itribez_android.Fragments.HomeFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.IOException

class CreatePostFragment : Fragment() {

    private val REQUEST_CAMERA_PERMISSION = 0
    private val REQUEST_GALLERY_PERMISSION = 1
    private val REQUEST_IMAGE_CAPTURE = 2
    private val REQUEST_GALLERY = 3

    private lateinit var imageView: ImageView
    private lateinit var descriptionEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var tagEditText: EditText
    private lateinit var imageView1: ImageView
    private var selectedOption: Int = -1
    private var selectedImageBase64: String? = null
    private var imagePath: Uri? = null

    private var config: MutableMap<String, Any> = HashMap()
    private var isMediaManagerInitialized = false
    private var publicId: String? = null

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
            initConfig()
            val description = descriptionEditText.text.toString()
            val location = locationEditText.text.toString()
            val tagsString = tagEditText.text.toString()
            val tags = ArrayList(tagsString.split(",").map { it.trim() })

            val jwtToken = SessionManager.getToken(requireContext())

            if (jwtToken != null) {
                val userId = SessionManager.getUserId(requireContext())
                val createPostRequest = CreatePostRequest(userId, location, description, tags, selectedImageBase64)
                sendCreatePostRequest(createPostRequest, jwtToken)
            } else {
                Log.e("ContentValues.TAG", "Token is null")
            }

            Log.d(TAG, "onCreateView: $description $location $tag ")
        }

        imageView1.setOnClickListener {
            navigateToHomeFragment()
        }

        return view
    }

    private fun navigateToHomeFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.placeHolder, HomeFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_CAMERA_PERMISSION
        )
    }

    private fun requestGalleryPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_GALLERY_PERMISSION
        )
    }

    private fun checkCameraPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestCameraPermission()
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
            requestGalleryPermission()
        } else {
            openGallery()
        }
    }

    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        } else {
            requestCameraPermission()
        }
    }

    private fun openGallery() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_GALLERY)
        } else {
            requestGalleryPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CAMERA_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Camera permission denied",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            REQUEST_GALLERY_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery()
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_GALLERY -> {
                    val selectedImageUri = data?.data
                    if (selectedImageUri != null) {
                        val (bitmap, uri) = convertUriToBitmap(selectedImageUri)
                        Glide.with(this).load(selectedImageUri).into(imageView)
                        imagePath = uri
                        selectedImageBase64 = bitmap?.let { convertImageToBase64(it) }
                    } else {
                        Log.e(TAG, "Selected image URI is null")
                    }
                }
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    imageView.setImageBitmap(imageBitmap)
                    selectedImageBase64 = convertImageToBase64(imageBitmap)
                    val uri = getImageUri(requireContext(), imageBitmap)
                    imagePath = uri
                }
            }
        }
    }

    private fun convertImageToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
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

    private fun convertUriToBitmap(uri: Uri): Pair<Bitmap?, Uri?> {
        val inputStream = requireActivity().contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        return Pair(bitmap, uri)
    }

    private fun initConfig() {
        if (!isMediaManagerInitialized) {
            config["cloud_name"] = "dzv1vpd2v"
            config["api_key"] = "874536865334618"
            config["api_secret"] = "zFa7GgUs0RTcXpOJryVgJXpi02U"
            MediaManager.init(requireContext(), config)
            isMediaManagerInitialized = true
        }
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

    private fun sendCreatePostRequest(createPostRequest: CreatePostRequest, jwtToken: String) {
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

                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    activity?.runOnUiThread {
                        Log.d(TAG, "Response successful: $responseBody")

                        val createpost = CreatePostFragment()
                        val args = Bundle()
                        args.putString("imageBase64", selectedImageBase64)
                        createpost.arguments = args

                        parentFragmentManager.beginTransaction()
                            .replace(R.id.placeHolder, createpost)
                            .addToBackStack(null)
                            .commit()
                    }
                } else {
                    activity?.runOnUiThread {
                        Log.d(TAG, "Request failed: ${response.code}")
                    }
                }
                MediaManager.get().upload(imagePath).callback(object : UploadCallback {
                    override fun onStart(requestId: String) {
                        Log.d(TAG, "onStart: ")
                    }

                    override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {
                        Log.d(TAG, "onStart: ")
                    }

                    override fun onSuccess(requestId: String, resultData: Map<*, *>) {
                        Log.d(TAG, "onStart: $requestId")
                        val cloudinaryResponse = Gson().toJson(resultData)
                        val publicId = extractPublicIdFromCloudinaryResponse(cloudinaryResponse)

                        activity?.runOnUiThread {
                            handleSuccessfulResponse(publicId)
                        }
                    }

                    override fun onError(requestId: String, error: ErrorInfo) {
                        Log.d(TAG, "onStart: ")
                    }

                    override fun onReschedule(requestId: String, error: ErrorInfo) {
                        Log.d(TAG, "onStart: ")
                    }
                }).dispatch()
            } catch (e: IOException) {
                activity?.runOnUiThread {
                    Log.e(TAG, "Network error: ${e.message}")
                }
            }
        }
    }

    private fun extractPublicIdFromCloudinaryResponse(responseBody: String?): String? {
        val jsonResponse = Gson().fromJson(responseBody, Map::class.java)
        val publicId = jsonResponse["public_id"]
        return publicId as? String
    }

    private fun handleSuccessfulResponse(publicId: String?) {
        if (publicId != null) {
            this.publicId = publicId
            Log.d(TAG, "Public ID: $publicId")
            Toast.makeText(requireContext(), "Public ID: $publicId", Toast.LENGTH_SHORT).show()
        } else {
            Log.e(TAG, "Failed to extract public ID from Cloudinary response")
            Toast.makeText(
                requireContext(),
                "Failed to extract public ID from Cloudinary response",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
