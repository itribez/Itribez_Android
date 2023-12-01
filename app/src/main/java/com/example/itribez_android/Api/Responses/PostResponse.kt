package com.example.itribez_android.Api.Responses

import com.google.gson.annotations.SerializedName
import java.util.Date

data class PostResponse(
    val nextCursor: String,
    val data: ArrayList<PostItem>
) {
    data class PostItem(
        @SerializedName("_id")
        var _id: String? = null,
        @SerializedName("user")
        var user: String? = null,
        @SerializedName("content")
        var content: String? = null,
        @SerializedName("photo")
        var photo: String? = null,
        @SerializedName("createdAt")
        var createdAt: Date? = null,
        @SerializedName("location")
        var location: String? = null,
        @SerializedName("tags")
        var tags: ArrayList<String>? = null,
        @SerializedName("likes")
        var likes: ArrayList<String>? = null,
        @SerializedName("comments")
        var comments: ArrayList<String>? = null,
        var isLiked:Boolean?=false,
        var likeCount:Int,

    )
}
