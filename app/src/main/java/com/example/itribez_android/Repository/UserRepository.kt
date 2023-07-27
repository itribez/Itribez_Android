package com.example.itribez_android.Repository

import com.example.itribez_android.Api.Methods.UserApi
import com.example.itribez_android.Api.Requests.LoginRequest
import com.example.itribez_android.Api.Requests.RegisterRequest
import com.example.itribez_android.Api.Responses.LoginResponse
import com.example.itribez_android.Api.Responses.RegisterResponse
import retrofit2.Response

class UserRepository {
    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse>? {
        return  UserApi.getApi()?.loginUser(loginRequest = loginRequest)
    }
    suspend fun registerUser(registerRequest: RegisterRequest): Response<RegisterResponse>?{
        return UserApi.getApi()?.registerUser(registerRequest = registerRequest)
    }
}