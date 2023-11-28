package com.example.itribez_android.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.itribez_android.Api.Requests.UnlikeRequest
import com.example.itribez_android.Api.Responses.BaseResponse
import com.example.itribez_android.Api.Responses.UnlikeResponse
import com.example.itribez_android.Repository.PostRepository
import kotlinx.coroutines.launch

class UnlikeViewModel(application: Application): AndroidViewModel(application) {
    val postRepo = PostRepository(getApplication())
    val unlikeResult : MutableLiveData<BaseResponse<UnlikeResponse?>> = MutableLiveData()

    fun unlikePost(userId : String,postId:String){
        unlikeResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try{
                val unlikeRequest = UnlikeRequest(
                    uid = userId,
                    postId = postId
                )
                val response = postRepo.unlikePost(postId,unlikeRequest = unlikeRequest)

                if (response?.code() == 200) {
                    unlikeResult.value = BaseResponse.Success(response.body())
                } else {
                    unlikeResult.value = BaseResponse.Error(response?.message())
                }
            }
            catch (ex:Exception){
                unlikeResult.value = BaseResponse.Error(ex.message)
            }
        }

    }
}
