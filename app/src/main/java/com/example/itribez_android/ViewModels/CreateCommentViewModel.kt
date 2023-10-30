package com.example.itribez_android.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.itribez_android.Api.Requests.CreateCommentRequest
import com.example.itribez_android.Api.Responses.BaseResponse
import com.example.itribez_android.Api.Responses.CreateCommentResponse
import com.example.itribez_android.Repository.PostRepository
import kotlinx.coroutines.launch

class CreateCommentViewModel(application: Application) : AndroidViewModel(application) {
    val postRepo = PostRepository(getApplication())
    val createCommentResult: MutableLiveData<BaseResponse<CreateCommentResponse>> = MutableLiveData()

    fun createComment(postId: String,userId:String,content:String){
        createCommentResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try{
                val createCommentRequest = CreateCommentRequest(
                    postId = postId,
                    userId = userId,
                    content = content
                )
                val response = postRepo.createComment(createCommentRequest = createCommentRequest)

                if (response?.code() == 200 ||response?.code() == 201) {
                    createCommentResult.value = BaseResponse.Success(response.body())
                } else {
                    createCommentResult.value = BaseResponse.Error("Something went wrong")
                }
            }
            catch (ex:Exception){
                createCommentResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}