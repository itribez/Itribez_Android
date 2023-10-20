package com.example.itribez_android.Api.Responses

import com.google.gson.annotations.SerializedName
import java.util.Date

data class MessageResponse(
    @SerializedName("_id")
    var _id:String ?=null,
    @SerializedName("sender")
    var sender:String?=null,
    @SerializedName("receiver")
    var receiver: String ?= null,
    @SerializedName("content")
    var content:String?=null,
)