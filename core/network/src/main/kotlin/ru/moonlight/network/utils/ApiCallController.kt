package ru.moonlight.network.utils

import android.util.Log
import kotlinx.coroutines.flow.first
import kotlinx.serialization.SerializationException
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import ru.moonlight.common.ApiResponse
import java.io.IOException
import javax.inject.Inject

internal sealed class ApiCall<T>(
    val body: T? = null,
    val headers: Map<String, String>? = null,
    val msg: String? = null,
    val code: Int? = null,
) {
    class Success<T>(body: T? = null, headers: Map<String, String>? = null, code: Int? = null) : ApiCall<T>(body = body, headers = headers, code = code)
    class Error<T>(msg: String? = null, code: Int? = null) : ApiCall<T>(msg = msg, code = code)
}

internal class ApiCallController @Inject constructor(
    private val networkMonitor: NetworkMonitor,
) {
    suspend fun <T> safeApiCall(call: suspend () -> Response<T>): ApiResponse<T> {
        if (!networkMonitor.isOnline.first()) {
            Log.e("Moonlight", "safeApiCall: No internet connection")
            return ApiResponse.Error(msg = "No internet connection")
        }

        return handleApiErrorForResponse("safeApiCall") {
            val response = call()
            Log.e("TAG", "safeApiCall: выполняю ${response.raw().networkResponse} код ${response.code()}")

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Log.d("Moonlight", "safeApiCall: Call successful code: ${response.code()}")
                    ApiResponse.Success(data = body, code = response.code())
                } else {
                    if (response.code() == 204) {
                        Log.d("Moonlight", "safeApiCall: Call successful code: ${response.code()}")
                        return@handleApiErrorForResponse ApiResponse.Success(data = null, code = response.code())
                    }
                    Log.e("Moonlight", "safeApiCall: Call unsuccessful code: ${response.code()}, msg: ${response.message()}")
                    ApiResponse.Error(msg = "Response body is null", code = response.code())
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Log.e("Moonlight", "safeApiCall: Call unsuccessful code: ${response.code()}, msg: $errorBody")
                ApiResponse.Error(errorBody, code = response.code())
            }
        }
    }

    suspend fun <T> safeApiCallWithHeader(call: suspend () -> Response<T>): ApiCall<T> {
        if (!networkMonitor.isOnline.first()) {
            Log.e("Moonlight", "safeApiCallWithHeader: No internet connection")
            return ApiCall.Error(msg = "No internet connection")
        }

        return handleApiErrorForCall("safeApiCallWithHeader") {
            val response = call()
            val headers = response.headers().toMap()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Log.d("Moonlight", "safeApiCallWithHeader: Call successful code: ${response.code()}")
                    ApiCall.Success(body = body, headers = headers, code = response.code())
                } else {
                    Log.e("Moonlight", "safeApiCallWithHeader: Call unsuccessful code: ${response.code()}, msg: ${response.message()}")
                    ApiCall.Error(msg = "Response body is null", code = response.code())
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Log.e("Moonlight", "safeApiCallWithHeader: Call unsuccessful code: ${response.code()}, msg: $errorBody")
                ApiCall.Error(errorBody, code = response.code())
            }
        }
    }

    suspend fun safeApiCallWithoutBody(call: suspend () -> Response<ResponseBody>): ApiResponse<Unit> {
        return handleApiErrorForResponse(callName = "safeApiCallWithoutBody") {
            val response = call()
            if (response.isSuccessful) {
                Log.d("Moonlight", "safeApiCallWithoutBody: Call successful code: ${response.code()}")
                ApiResponse.Success()
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Log.e("Moonlight", "safeApiCallWithoutBody: Call unsuccessful code: ${response.code()}, msg: $errorBody")
                ApiResponse.Error(errorBody)
            }
        }
    }

    private suspend fun <T> handleApiErrorForResponse(callName: String, block: suspend () -> ApiResponse<T>): ApiResponse<T> {
        return try {
            block()
        } catch (e: IOException) {
            Log.e(callName, "Network error: ${e.localizedMessage}")
            ApiResponse.Error("Network error: ${e.localizedMessage}")
        } catch (e: HttpException) {
            Log.e(callName, "HTTP error: ${e.code()} ${e.message}")
            ApiResponse.Error("HTTP error: ${e.code()} ${e.message}")
        } catch (e: SerializationException) {
            Log.e(callName, "Parsing error: ${e.localizedMessage}")
            ApiResponse.Error("Parsing error: ${e.localizedMessage}")
        } catch (e: Exception) {
            Log.e(callName, "Unexpected error: ${e.localizedMessage ?: "No message available"}")
            ApiResponse.Error("Unexpected error: ${e.localizedMessage ?: "No message available"}")
        }
    }

    private suspend fun <T> handleApiErrorForCall(callName: String, block: suspend () -> ApiCall<T>): ApiCall<T> {
        return try {
            block()
        } catch (e: IOException) {
            Log.e(callName, "Network error: ${e.localizedMessage}")
            ApiCall.Error("Network error: ${e.localizedMessage}")
        } catch (e: HttpException) {
            Log.e(callName, "HTTP error: ${e.code()} ${e.message}")
            ApiCall.Error("HTTP error: ${e.code()} ${e.message}")
        } catch (e: SerializationException) {
            Log.e(callName, "Parsing error: ${e.localizedMessage}")
            ApiCall.Error("Parsing error: ${e.localizedMessage}")
        } catch (e: Exception) {
            Log.e(callName, "Unexpected error: ${e.localizedMessage ?: "No message available"}")
            ApiCall.Error("Unexpected error: ${e.localizedMessage ?: "No message available"}")
        }
    }

}