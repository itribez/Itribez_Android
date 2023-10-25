package com.example.itribez_android.Api.Requests

import com.google.gson.annotations.SerializedName

data class MessageRequest (
    @SerializedName("userID")
    var userID: String?=null
)