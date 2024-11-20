package ru.moonlight.network.utils

sealed class ApiResponse<T>(
    val data: T? = null,
    val msg: String? = null,
) {
    class Success<T>(data: T? = null) : ApiResponse<T>(data = data)
    class Error<T>(msg: String? = null) : ApiResponse<T>(msg = msg)
}