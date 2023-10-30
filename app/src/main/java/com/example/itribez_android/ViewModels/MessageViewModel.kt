package com.example.itribez_android.ViewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.itribez_android.Api.Requests.PostRequest
import com.example.itribez_android.Api.Responses.BaseResponse
import com.example.itribez_android.Api.Responses.PostResponse
import com.example.itribez_android.Repository.UserRepository
import kotlinx.coroutines.launch

class MessageViewModel(application: Application) : AndroidViewModel(application) {
    val userRepo = UserRepository()
    val postResult: MutableLiveData<BaseResponse<PostResponse?>> = MutableLiveData()

//    fun postUser(token: String) {
//
//        postResult.value = BaseResponse.Loading()
//        viewModelScope.launch {
//            try {
//                val postRequest = PostRequest(token)
//               // val response = userRepo.getPost(postRequest.toString())
//                Log.d("ResponseCode", response?.code().toString())
//                if (response?.code() == 200) {
//                    postResult.value = BaseResponse.Success(response.body())
//                } else {
//                    postResult.value = BaseResponse.Error(response?.message())
//                }
//            } catch (ex: Exception) {
//                postResult.value = BaseResponse.Error(ex.message)
//            }
//        }
//    }
}