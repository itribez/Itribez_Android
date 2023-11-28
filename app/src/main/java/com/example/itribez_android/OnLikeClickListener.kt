package com.example.itribez_android

import com.example.itribez_android.Api.Responses.PostResponse
import com.example.itribez_android.dataclasses.DataClassPosts

interface OnLikeClickListener {
    fun onLikeClick(post: PostResponse.PostItem, liked: Boolean)
}
