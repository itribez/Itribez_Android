package com.example.itribez_android.ViewModels

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.itribez_android.Api.Responses.BaseResponse
import com.example.itribez_android.Api.Responses.CommentResponse
import com.example.itribez_android.Repository.PostRepository
import kotlinx.coroutines.launch

class CommentViewModel (application: Application) : AndroidViewModel(application){
    val postRepo = PostRepository()
    val commentResult: MutableLiveData<BaseResponse<CommentResponse>> = MutableLiveData()

    fun getComments() {
        //Log.d(TAG, "API Request: $postId")
        commentResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = postRepo.getComments()
                Log.d(TAG, "API Response Code: ${response?.code()}")
                if (response?.code()==200) {
                    Log.d(TAG, "API Response: ${response.body()}")
                    commentResult.value = BaseResponse.Success(response.body())
                } else {
                    Log.e(TAG, "API Call Failed with code: ${response?.code()}")
                    Log.d(TAG,"response unsuccessful: ${response?.message()}")
                    //  Log.e(TAG, "API Call Failed: ${t.message}")
                    commentResult.value = BaseResponse.Error("Failed to fetch comments")
                }
            } catch (ex: Exception) {
                commentResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}