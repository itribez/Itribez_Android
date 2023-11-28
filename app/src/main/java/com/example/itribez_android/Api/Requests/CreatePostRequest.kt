package com.example.itribez_android.Api.Requests

import com.google.gson.annotations.SerializedName

data class CreatePostRequest(
    @SerializedName("userId")
    val userId: String?,
    @SerializedName("location")
    val location: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("tags")
    val tags:  ArrayList<String>,
    @SerializedName("photo")
    val photo: String?
)
