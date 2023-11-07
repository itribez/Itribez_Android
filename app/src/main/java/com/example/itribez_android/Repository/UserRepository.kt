package com.example.itribez_android.Repository

import android.content.Context
import android.util.Log
import com.example.itribez_android.Api.Methods.UserApi
import com.example.itribez_android.Api.Requests.LoginRequest
import com.example.itribez_android.Api.Requests.PostRequest
import com.example.itribez_android.Api.Requests.RegisterRequest
import com.example.itribez_android.Api.Responses.LoginResponse
import com.example.itribez_android.Api.Responses.PostResponse
import com.example.itribez_android.Api.Responses.RegisterResponse
import com.example.itribez_android.utils.SessionManager
import retrofit2.Response

class UserRepository(private val context: Context) {
    private val bearerToken: String
        get() = "Bearer ${SessionManager.getToken(context)}"
    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse>? {
        return  UserApi.getApi()?.loginUser(loginRequest = loginRequest)
    }
    suspend fun registerUser(registerRequest: RegisterRequest): Response<RegisterResponse>?{
        return UserApi.getApi()?.registerUser(registerRequest = registerRequest)
    }
    suspend fun getPost(): Response<PostResponse>? {
        // var response: Response<PostResponse>? = null
        Log.d("PostRepository", "Bearer Token: $bearerToken")
        return try {
            Log.d("PostRepository", "Bearer Token: $bearerToken")
            val response = UserApi.getApi()?.getAllPost(bearerToken)
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