package ru.moonlight.common

sealed class ApiResponse<T>(
    val data: T? = null,
    val code: Int? = null,
    val msg: String? = null,
) {
    class Success<T>(data: T? = null, code: Int? = null) : ApiResponse<T>(data = data, code = code)
    class Error<T>(msg: String? = null, code: Int? = null) : ApiResponse<T>(msg = msg, code = code)
}