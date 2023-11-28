package com.example.itribez_android.Api.Requests

import com.google.gson.annotations.SerializedName

data class LikeRequest(
    @SerializedName("userId")
    val uid : String,
    @SerializedName("postId")
    val postId : String
)
