package com.example.itribez_android.Api.Responses

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token")
    var token: String?=null,
    @SerializedName("userId")
    var userId:String?=null
)
