package com.example.itribez_android.Fragments

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel(){
    private val _selectedImage = MutableLiveData<Uri>()
    val selectedImage: LiveData<Uri> get() = _selectedImage

    fun setSelectedImage(imageUri: Uri) {
        _selectedImage.value = imageUri
    }
}