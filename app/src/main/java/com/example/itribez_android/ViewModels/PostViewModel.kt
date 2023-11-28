//package com.example.itribez_android.ViewModels
//
//import android.app.Application
//import android.content.ContentValues.TAG
//import android.content.Context
//import android.util.Log
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.viewModelScope
//import com.example.itribez_android.Api.Requests.PostRequest
//import com.example.itribez_android.Api.Responses.BaseResponse
//import com.example.itribez_android.Api.Responses.PostResponse
//import com.example.itribez_android.Repository.UserRepository
//import kotlinx.coroutines.launch
//
//class PostViewModel(application: Application) : AndroidViewModel(application) {
//    val userRepo = UserRepository(getApplication())
//    val postResult: MutableLiveData<BaseResponse<PostResponse?>> = MutableLiveData()
//
//    fun getPosts() {
//        postResult.value = BaseResponse.Loading()
//        viewModelScope.launch {
//            try {
//                // val postRequest = PostRequest(token)
//                val response = userRepo.getPost()
//                val context : Context = getApplication()
//                Log.d("ResponseCode", response?.code().toString())
//                if (response?.code() == 200 || response?.code() == 201) {
//                    Log.d(TAG, "postUser: ${response.body()}")
//                    postResult.value = BaseResponse.Success(response.body())
//                    // SessionManager.saveAuthToken(context,loginResponse!!.token)
//                    // SessionManager.saveUserId(context,loginResponse.userId)
//                } else {
//                    Log.e(TAG, "API Call Failed with code: ${response?.code()}")
//                    Log.d(TAG,"response unsuccessful: ${response?.message()}")
//                    postResult.value = BaseResponse.Error(response?.message())
//                }
//            } catch (ex: Exception) {
//                postResult.value = BaseResponse.Error(ex.message)
//            }
//        }
//    }
//}
package com.example.itribez_android.ViewModels

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.itribez_android.Api.Requests.PostRequest
import com.example.itribez_android.Api.Responses.BaseResponse
import com.example.itribez_android.Api.Responses.PostResponse
import com.example.itribez_android.Repository.UserRepository
import kotlinx.coroutines.launch

class PostViewModel(application: Application) : AndroidViewModel(application) {
    val userRepo = UserRepository(getApplication())
    val postResult: MutableLiveData<BaseResponse<PostResponse?>> = MutableLiveData()

    fun getPosts() {
        postResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                // val postRequest = PostRequest(token)
                val response = userRepo.getPost()
                val context : Context = getApplication()
                Log.d("ResponseCode", response?.code().toString())
                if (response?.code() == 200 || response?.code() == 201) {
                    Log.d(TAG, "postUser: ${response.body()}")
                    postResult.value = BaseResponse.Success(response.body())
                    // SessionManager.saveAuthToken(context,loginResponse!!.token)
                    // SessionManager.saveUserId(context,loginResponse.userId)
                } else {
                    Log.e(TAG, "API Call Failed with code: ${response?.code()}")
                    Log.d(TAG,"response unsuccessful: ${response?.message()}")
                    postResult.value = BaseResponse.Error(response?.message())
                }
            } catch (ex: Exception) {
                postResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}
