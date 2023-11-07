package com.example.itribez_android.Api.Requests

import com.google.gson.annotations.SerializedName

data class NotificationRequest(
    @SerializedName("userId")
    val userId: String
)