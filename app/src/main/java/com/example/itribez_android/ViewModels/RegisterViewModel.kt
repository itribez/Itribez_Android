package com.example.itribez_android.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.itribez_android.Api.Requests.RegisterRequest
import com.example.itribez_android.Api.Responses.BaseResponse
import com.example.itribez_android.Api.Responses.RegisterResponse
import com.example.itribez_android.Repository.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel (application: Application) : AndroidViewModel(application) {

    val userRepo = UserRepository()
    val registerResult: MutableLiveData<BaseResponse<RegisterResponse?>> = MutableLiveData()
    fun registerUser(email: String, password: String) {

        registerResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val registerRequest = RegisterRequest(
                     email, password
                )
                val response = userRepo.registerUser(registerRequest = registerRequest)
//                Log.d("ResponseCode", response?.code().toString())
                if (response?.code() == 200 || response?.code() == 201) {
                    registerResult.value = BaseResponse.Success(response.body())
                } else {
                    registerResult.value = BaseResponse.Error(response?.message())
                }
            } catch (ex: Exception) {
                registerResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}