package com.example.itribez_android.Api.Responses

import com.google.gson.annotations.SerializedName

data class CreateCommentResponse(
    val content : String,

    @SerializedName("fullName")
    val fullName: String
)
