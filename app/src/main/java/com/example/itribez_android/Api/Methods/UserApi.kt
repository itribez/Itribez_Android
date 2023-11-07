package com.example.itribez_android.Api.Methods

import com.example.itribez_android.Api.ApiClient
import com.example.itribez_android.Api.Requests.LoginRequest
import com.example.itribez_android.Api.Requests.RegisterRequest
import com.example.itribez_android.Api.Responses.LoginResponse
import com.example.itribez_android.Api.Responses.PostResponse
import com.example.itribez_android.Api.Responses.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    @POST("/user/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>
    @POST("/user/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse>
    @GET("/post/posts")
    suspend fun getAllPost(@Header("Authorization")token:String):Response<PostResponse>
    companion object {
        fun getApi(): UserApi? {
            return ApiClient.client?.create(UserApi::class.java)
        }
    }
}