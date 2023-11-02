package com.example.itribez_android.Repository

import android.content.Context
import android.util.Log
import com.example.itribez_android.Api.Methods.UserApi
import com.example.itribez_android.Api.Requests.CreateCommentRequest
import com.example.itribez_android.Api.Requests.LikeRequest
import com.example.itribez_android.Api.Requests.UnlikeRequest
import com.example.itribez_android.Api.Responses.CommentResponse
import com.example.itribez_android.Api.Responses.CreateCommentResponse
import com.example.itribez_android.Api.Responses.LikeResponse
import com.example.itribez_android.Api.Responses.UnlikeResponse
import com.example.itribez_android.utils.SessionManager
import retrofit2.Response

class PostRepository(private val context: Context) {
    private val bearerToken: String
        get() = "Bearer ${SessionManager.getToken(context)}"
    suspend fun likePost(likeRequest: LikeRequest): Response<LikeResponse>?{
        return UserApi.getApi()?.likePost(bearerToken,likeRequest = likeRequest)
    }
    suspend fun unlikePost(unlikeRequest: UnlikeRequest):Response<UnlikeResponse>?{
        return UserApi.getApi()?.unlikePost(bearerToken,unlikeRequest = unlikeRequest)
    }

    suspend fun createComment(createCommentRequest: CreateCommentRequest):Response<CreateCommentResponse>?{
        return UserApi.getApi()?.createComment(bearerToken,createCommentRequest = createCommentRequest)
    }

    suspend fun getComments(): Response<CommentResponse>? {
        var response: Response<CommentResponse>? = null
        Log.d("PostRepository", "Bearer Token: $bearerToken")
        return try {
            Log.d("PostRepository", "Bearer Token: $bearerToken")
            val response = UserApi.getApi()?.getComments("$bearerToken")
            Log.d("PostRepository", "Response Code: ${response?.code()}")
            Log.d("PostRepository", "Response Body: ${response?.body()}")
            response
        } catch (e: Exception) {
            Log.e("PostRepository", "Exception: ${e.message}")
            // Handle exceptions, such as network errors
            null
        }
    }
}