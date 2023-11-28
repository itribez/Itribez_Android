package com.example.itribez_android.Api.Responses

import com.example.itribez_android.Models.Post
import com.google.gson.annotations.SerializedName

data class NotificationResponseModel(
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalPages")
    val totalPages: Int,
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("notifications")
    val notifications: List<Notification>
) {
    data class Notification(
        @SerializedName("_id")
        val id: String,
        @SerializedName("receiver")
        val receiver: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("sender")
        val sender: Sender,
        @SerializedName("post")
        val post: Post,
        @SerializedName("read")
        val read: Boolean,
        @SerializedName("createdAt")
        val createdAt: String
    ) {
        data class Sender(
            @SerializedName("_id")
            val id: String,
            @SerializedName("firstName")
            val firstName: String,
            @SerializedName("lastName")
            val lastName: String
        ) {
            data class Post(
                @SerializedName("_id")
                val id: String
            )
        }
    }
}
