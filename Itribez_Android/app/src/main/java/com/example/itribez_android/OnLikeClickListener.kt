package com.example.itribez_android

import com.example.itribez_android.dataclasses.DataClassPosts

interface OnLikeClickListener {



    fun onLikeClick(post: DataClassPosts, liked: Boolean)
}