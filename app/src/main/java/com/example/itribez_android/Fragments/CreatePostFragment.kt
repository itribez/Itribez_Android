package com.example.itribez_android.Fragments

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.itribez_android.R
import java.io.File

class CreatePostFragment : Fragment() {

    private lateinit var descriptionEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var tagEditText: EditText
    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_createpost, container, false)

        descriptionEditText = view.findViewById(R.id.description)
        locationEditText = view.findViewById(R.id.location)
        tagEditText = view.findViewById(R.id.Tag)
        imageView = view.findViewById(R.id.img)

        imageView.setOnClickListener {
            val addmediaFragment = AddMediaToPostFragment()

            parentFragmentManager.beginTransaction()
                .replace(R.id.placeHolder, addmediaFragment)
                .addToBackStack(null)
                .commit()
        }

        // Set up the root view to listen for touch events
        view.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
        // Retrieve the selected image URI from arguments
        val selectedImageUriString = arguments?.getString("selectedImageUri")
        if (selectedImageUriString != null) {
            val selectedImageUri = Uri.parse(selectedImageUriString)
            val file = File(selectedImageUri.path)
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            imageView.setImageBitmap(bitmap)
            Log.d("CreatePostFragment", "URI: $selectedImageUriString")
        } else {
            Log.e("CreatePostFragment", "SelectedImageUri is null")
        }

        val postButton: Button = view.findViewById(R.id.postbutton)
        postButton.setOnClickListener {
            val description = descriptionEditText.text.toString()
            val location = locationEditText.text.toString()
            val tag = tagEditText.text.toString()

            Log.d("CreatePostFragment", "Description: $description")
            Log.d("CreatePostFragment", "Location: $location")
            Log.d("CreatePostFragment", "Tag: $tag")

            hideKeyboard()
            Toast.makeText(requireContext(), "You created a post successfully", Toast.LENGTH_SHORT).show()

        }

        return view
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    companion object {
        fun newInstance(selectedImageUri: String): CreatePostFragment {
            val fragment = CreatePostFragment()
            val bundle = Bundle().apply {
                putString("selectedImageUri", selectedImageUri)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}