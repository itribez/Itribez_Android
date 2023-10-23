package com.example.itribez_android.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.itribez_android.Api.Requests.LoginRequest
import com.example.itribez_android.Api.Responses.BaseResponse
import com.example.itribez_android.Api.Responses.LoginResponse
import com.example.itribez_android.Repository.UserRepository
import com.example.itribez_android.utils.SessionManager
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val userRepo = UserRepository()
    val loginResult: MutableLiveData<BaseResponse<LoginResponse?>> = MutableLiveData()
    var authToken: String? = null
    fun loginUser(email: String, pwd: String) {
        loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(
                    password = pwd,
                    email = email
                )
                val response = userRepo.loginUser(loginRequest = loginRequest)
                if (response?.code() == 200) {
                    val loginResponse = response.body()
                    loginResult.value = BaseResponse.Success(response.body())
                    authToken = loginResponse?.token
                    loginResponse?.token = SessionManager.USER_TOKEN
                    loginResponse?.userId = SessionManager.USER_ID
                } else {
                    loginResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                loginResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}