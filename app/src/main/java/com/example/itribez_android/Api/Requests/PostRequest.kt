package com.example.itribez_android.Api.Requests

import com.google.gson.annotations.SerializedName

data class PostRequest(
    @SerializedName("token")
    var token: String?=null
)
