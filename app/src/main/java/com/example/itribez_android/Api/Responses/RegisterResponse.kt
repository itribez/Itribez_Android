package com.example.itribez_android.Api.Responses

import com.google.gson.annotations.SerializedName

data class RegisterResponse (
    @SerializedName("token")
    var token: String ?= null,
    @SerializedName("message")
    var message:String?=null
)
