package com.example.itribez_android.Repository

import android.util.Log
import com.example.itribez_android.Api.Methods.UserApi
import com.example.itribez_android.Api.Requests.LikeRequest
import com.example.itribez_android.Api.Requests.UnlikeRequest
import com.example.itribez_android.Api.Responses.CommentResponse
import com.example.itribez_android.Api.Responses.LikeResponse
import com.example.itribez_android.Api.Responses.UnlikeResponse
import retrofit2.Response

class PostRepository {
    suspend fun likePost(likeRequest: LikeRequest): Response<LikeResponse>?{
        return UserApi.getApi()?.likePost(likeRequest = likeRequest)
    }
    suspend fun unlikePost(unlikeRequest: UnlikeRequest):Response<UnlikeResponse>?{
        return UserApi.getApi()?.unlikePost(unlikeRequest = unlikeRequest)
    }

    private val bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NGNhZDJhYzU3NDhmNWRhMTNhYzViNTgiLCJpYXQiOjE2OTU1OTc3MTR9.du9gCZ9OwVW1H1VyqFJGf-jAsMGw5za799kUffLgi3Y"
    suspend fun getComments(): Response<CommentResponse>? {
        var response: Response<CommentResponse>? = null
        Log.d("PostRepository", "Bearer Token: $bearerToken")
        return try {
            val response = UserApi.getApi()?.getComments("Bearer $bearerToken")
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