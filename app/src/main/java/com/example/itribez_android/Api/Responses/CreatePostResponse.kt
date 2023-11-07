package com.example.itribez_android.Api.Responses

import com.google.gson.annotations.SerializedName
class CreatePostResponse (
    @SerializedName("user")
    val user: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("comments")
    val comments: List<Any>,
    @SerializedName("likes")
    val likes: List<Any>,
    @SerializedName("_id")
    val id: String,
    @SerializedName("__v")
    val version: Int
)
