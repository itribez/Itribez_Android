package com.example.itribez_android.Api.Responses

data class CommentResponse(
    val nextCursor: String,
    val data: List<CommentItem>
) {
    data class CommentItem(
        val _id: String,
        val user: User,
        val post: String,
        val content: String,
        val createdAt: String,
        val __v: Int
    ) {
        data class User(
            val _id: String,
            val firstName: String,
            val lastName: String
        )
    }
}
