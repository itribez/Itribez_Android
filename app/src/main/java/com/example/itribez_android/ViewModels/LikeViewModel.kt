package com.example.itribez_android.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.itribez_android.Api.Requests.LikeRequest
import com.example.itribez_android.Api.Responses.BaseResponse
import com.example.itribez_android.Api.Responses.LikeResponse
import com.example.itribez_android.Repository.PostRepository
import kotlinx.coroutines.launch

class LikeViewModel(application: Application) : AndroidViewModel(application){
    val postRepo = PostRepository(getApplication())
    val likeResult : MutableLiveData<BaseResponse<LikeResponse?>> = MutableLiveData()

    fun likePost(userId : String,postId:String){
        likeResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try{
                val likeRequest = LikeRequest(
                    userId
                    , postId
                )
                val response = postRepo.likePost(postId,likeRequest = likeRequest)

                if (response?.code() == 200) {
                    likeResult.value = BaseResponse.Success(response.body())
                } else {
                    likeResult.value = BaseResponse.Error("Something went wrong")
                }
            }
            catch (ex:Exception){
                likeResult.value = BaseResponse.Error(ex.message)
            }
        }

    }
}
