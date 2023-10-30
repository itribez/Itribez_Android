package com.example.itribez_android.Api.Requests

import com.google.gson.annotations.SerializedName

data class CommentRequest(
    @SerializedName("postId")
    val postId: String
)
