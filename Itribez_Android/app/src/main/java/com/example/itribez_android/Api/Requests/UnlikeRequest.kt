package com.example.itribez_android.Api.Requests

import com.google.gson.annotations.SerializedName

data class UnlikeRequest(
    @SerializedName("userId")
    val uid : String
)

