package ru.moonlight.network.utils

import kotlinx.coroutines.flow.first
import kotlinx.serialization.SerializationException
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

internal sealed class ApiCall<T>(
    val body: T? = null,
    val headers: Map<String, String>? = null,
    val msg: String? = null
) {
    class Success<T>(body: T? = null, headers: Map<String, String>? = null) : ApiCall<T>(body = body, headers = headers)
    class Error<T>(msg: String? = null) : ApiCall<T>(msg = msg)
}

internal class ApiCallController @Inject constructor(
    private val networkMonitor: NetworkMonitor,
) {
    suspend fun <T> safeApiCall(call: suspend () -> Response<T>): ApiResponse<T> {
        if (!networkMonitor.isOnline.first()) {
            return ApiResponse.Error(msg = "No internet connection")
        }
        return try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null)
                    ApiResponse.Success(data = body)
                else
                    ApiResponse.Error(msg = "Response body is null")
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                ApiResponse.Error(errorBody)
            }
        } catch (e: IOException) { // Сетевая ошибка
            ApiResponse.Error("Network error: ${e.localizedMessage}")
        } catch (e: HttpException) { // Ошибки HTTP (например, 4xx, 5xx)
            ApiResponse.Error("HTTP error: ${e.code()} ${e.message}")
        } catch (e: SerializationException) { // Ошибки при парсинге ответа
            ApiResponse.Error("Parsing error: ${e.localizedMessage}")
        } catch (e: Exception) { // Остальные ошибки
            ApiResponse.Error("Unexpected error: ${e.localizedMessage ?: "No message available"}")
        }
    }

    suspend fun <T> safeApiCallWithHeader(call: suspend () -> Response<T>): ApiCall<T> {
        if (!networkMonitor.isOnline.first()) {
            return ApiCall.Error(msg = "No internet connection")
        }
        return try {
            val response = call()
            val headers = response.headers().toMap()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null)
                    ApiCall.Success(body = body, headers = headers)
                else
                    ApiCall.Error(msg = "Response body is null")
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                ApiCall.Error(errorBody)
            }
        } catch (e: IOException) { // Сетевая ошибка
            ApiCall.Error("Network error: ${e.localizedMessage}")
        } catch (e: HttpException) { // Ошибки HTTP (например, 4xx, 5xx)
            ApiCall.Error("HTTP error: ${e.code()} ${e.message}")
        } catch (e: SerializationException) { // Ошибки при парсинге ответа
            ApiCall.Error("Parsing error: ${e.localizedMessage}")
        } catch (e: Exception) { // Остальные ошибки
            ApiCall.Error("Unexpected error: ${e.localizedMessage ?: "No message available"}")
        }
    }

    suspend fun safeApiCallWithoutBody(call: suspend () -> Response<ResponseBody>): ApiResponse<Nothing> {
        if (!networkMonitor.isOnline.first()) {
            return ApiResponse.Error(msg = "No internet connection")
        }
        return try {
            val response = call()
            if (response.isSuccessful) {
                ApiResponse.Success()
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                ApiResponse.Error(errorBody)
            }
        } catch (e: IOException) { // Сетевая ошибка
            ApiResponse.Error("Network error: ${e.localizedMessage}")
        } catch (e: HttpException) { // Ошибки HTTP (например, 4xx, 5xx)
            ApiResponse.Error("HTTP error: ${e.code()} ${e.message}")
        } catch (e: SerializationException) { // Ошибки при парсинге ответа
            ApiResponse.Error("Parsing error: ${e.localizedMessage}")
        } catch (e: Exception) { // Остальные ошибки
            ApiResponse.Error("Unexpected error: ${e.localizedMessage ?: "No message available"}")
        }
    }
}