package com.example.itribez_android.dataclasses

data class DataClassPosts(var postId : String, var postImage:Int,var caption:String,var comments:ArrayList<DataClassComments> = ArrayList(),var publisher:String, var isLiked : Boolean,var likeCount:Int)



