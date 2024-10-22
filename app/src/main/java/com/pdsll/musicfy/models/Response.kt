package com.pdsll.musicfy.models

sealed class Response<out T> {
    object Loading:Response<Nothing>()
    data class Success<out T>(
        val data: T?
    ): Response<T>()
    data class Failure(
        val e:Exception
    ):Response<Nothing>()
}

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val exception: Throwable) : Result<Nothing>()
}