package com.example.itribez_android.Api.Methods

import com.example.itribez_android.Api.ApiClient
import com.example.itribez_android.Api.Requests.CreateCommentRequest
import com.example.itribez_android.Api.Requests.LikeRequest
import com.example.itribez_android.Api.Requests.LoginRequest
import com.example.itribez_android.Api.Requests.RegisterRequest
import com.example.itribez_android.Api.Requests.UnlikeRequest
import com.example.itribez_android.Api.Responses.CommentResponse
import com.example.itribez_android.Api.Responses.CreateCommentResponse
import com.example.itribez_android.Api.Responses.LikeResponse
import com.example.itribez_android.Api.Responses.LoginResponse
import com.example.itribez_android.Api.Responses.RegisterResponse
import com.example.itribez_android.Api.Responses.UnlikeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApi {
    @POST("/user/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>
    @POST("/user/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    //@Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NGI5NTBiZjU1MGU2NjEwYjg1ZmVkOTYiLCJpYXQiOjE2OTUwNzgxNjh9.7inPEfis6ChMWuV3EKwNLqn_i7QxmvRnHhbq5t6rNSM")
    @PUT("/post/64b9d0c0e69fc1dcccd8fc3b/like")  // Adjust the endpoint path as needed
    suspend fun likePost(@Header("Authorization") token: String,@Body likeRequest: LikeRequest): Response<LikeResponse>

   // @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NGI5NTBiZjU1MGU2NjEwYjg1ZmVkOTYiLCJpYXQiOjE2OTUwNzgxNjh9.7inPEfis6ChMWuV3EKwNLqn_i7QxmvRnHhbq5t6rNSM")
    @PUT("/post/64b9d0c0e69fc1dcccd8fc3b/unlike")  // Adjust the endpoint path as needed
    suspend fun unlikePost(@Header("Authorization") token: String,@Body unlikeRequest: UnlikeRequest): Response<UnlikeResponse>

    @POST("comment/create")
    suspend fun createComment(@Header("Authorization") token: String,@Body createCommentRequest: CreateCommentRequest) : Response<CreateCommentResponse>
    @GET("comment/post/6510b73f825da39d0826e8ce/comments")
    suspend fun getComments(@Header("Authorization") token: String) : Response<CommentResponse>
    companion object {
        fun getApi(): UserApi? {
            return ApiClient.client?.create(UserApi::class.java)
        }
    }
}