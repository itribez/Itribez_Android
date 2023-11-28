package com.example.itribez_android.Api.Requests

import com.google.gson.annotations.SerializedName

data class CreateCommentRequest(
    @SerializedName("postId")
    val postId: String,
    @SerializedName("userId")
    val userId : String,
    @SerializedName("content")
    val content:String
)
