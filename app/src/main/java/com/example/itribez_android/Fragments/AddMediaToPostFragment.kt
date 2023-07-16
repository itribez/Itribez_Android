package com.example.itribez_android.Fragments


import com.example.itribez_android.R
import android.Manifest
import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class AddMediaToPostFragment : Fragment() {
    private val REQUEST_PERMISSIONS_CODE = 123
    private lateinit var gridView: GridView
    private lateinit var adapter: ImageAdapter
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_beforepost, container, false)
        gridView = view.findViewById(R.id.galleryGridView)
        val addMediaButton: Button = view.findViewById(R.id.addmedia)

        addMediaButton.setOnClickListener {
            val fragment = CreatePostFragment()
            val bundle = Bundle().apply {
                putParcelable("selectedImageUri", selectedImageUri)
            }
            fragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.placeHolder, fragment)
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (checkPermissions()) {
            loadGalleryImages()
        } else {
            requestPermissions()
        }
    }
    

    private fun checkPermissions(): Boolean {
        val readStoragePermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return readStoragePermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_PERMISSIONS_CODE
        )
    }

    private fun loadGalleryImages() {
        val images = mutableListOf<Uri>()
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME
        )
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val cursor = requireContext().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )

        cursor?.use {
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                images.add(contentUri)
            }
        }

        adapter = ImageAdapter(requireContext(), images)
        gridView.adapter = adapter

        gridView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val selectedImageUri = images[position]
                // Handle the click event for the selected image URI
                showSelectedImage(selectedImageUri)
            }
    }

    private class ImageAdapter(private val context: Context, private val images: List<Uri>) :
        BaseAdapter() {

        override fun getCount(): Int {
            return images.size
        }

        override fun getItem(position: Int): Any {
            return images[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val imageView: ImageView

            if (convertView == null) {
                imageView = ImageView(context)
                imageView.layoutParams = ViewGroup.LayoutParams(250, 250)
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                imageView.adjustViewBounds = true
            } else {
                imageView = convertView as ImageView
            }

            val imageUri = images[position]
            Glide.with(context)
                .load(imageUri)
                .into(imageView)

            val roundedCornerDrawable = ContextCompat.getDrawable(context, R.drawable.gridview_background)
            imageView.background = roundedCornerDrawable

            return imageView
        }
    }
    private fun showSelectedImage(uri: Uri) {
        // Display the selected image in an ImageView
        val imageView: ImageView = requireView().findViewById(R.id.imageView)
        Glide.with(requireContext())
            .load(uri)
            .into(imageView)
    }
}
