package com.example.itribez_android.Api.Responses

sealed class BaseResponse <out T>{
/*
    class Success<T>(data: T) : BaseResponse<T>(data)
    class Error<T>(message: String?, data: T? = null) : BaseResponse<T>(data, message)
    class Loading<T> : BaseResponse<T>()

*/
    data class Success<out T>(val data: T? = null) : BaseResponse<T>()
    data class Loading(val nothing: Nothing?=null) : BaseResponse<Nothing>()
    data class Error(val msg: String?) : BaseResponse<Nothing>()
}