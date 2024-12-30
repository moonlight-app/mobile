package ru.moonlight.network.interceptor

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.moonlight.network.BuildConfig
import ru.moonlight.network.api.AuthApi
import ru.moonlight.network.model.auth.LoginResponse
import ru.moonlight.network.utils.SessionManager
import javax.inject.Inject

internal class UpdateTokenInterceptor @Inject constructor(
    private val sessionManager: SessionManager,
) : Interceptor {

    companion object {
        private const val MAX_RETRY_COUNT = 3 // Максимальное количество попыток обновления токена
        private const val RETRY_HEADER = "X-Retry-Attempt" // Кастомный заголовок для отслеживания попыток
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val retryCount = originalRequest.header(RETRY_HEADER)?.toIntOrNull() ?: 0

        // Проверяем лимит попыток
        if (retryCount > MAX_RETRY_COUNT) {
            return chain.proceed(originalRequest) // Возвращаем оригинальный запрос без повторных попыток
        }

        val response = chain.proceed(originalRequest)

        // Перехват только для 401 и 403
        if (response.code == 401 || response.code == 403) {
            response.close() // Закрываем исходный ответ

            // Пытаемся обновить токен
            val newAccessToken = runBlocking {
                val refreshToken = sessionManager.refreshToken.first()
                getNewAccessToken(refreshToken)
            }

            return if (newAccessToken != null) {
                // Сохраняем новые токены и повторяем запрос
                runBlocking {
                    sessionManager.saveTokens(
                        accessPair = Pair(newAccessToken.accessToken, newAccessToken.accessTokenExpiresIn),
                        refreshPair = Pair(newAccessToken.refreshToken, newAccessToken.refreshTokenExpiresIn),
                    )
                }

                val newRequest = addAuthorizationHeader(originalRequest.newBuilder()
                    .header(RETRY_HEADER, (retryCount + 1).toString()) // Увеличиваем счётчик попыток
                    .build(), newAccessToken.accessToken)
                chain.proceed(newRequest)
            } else {
                runBlocking {
                    // Если не удалось обновить токен, очищаем сессию
                    sessionManager.clearTokens()
                }
                response
            }
        }

        return response
    }

    private fun addAuthorizationHeader(request: Request, accessToken: String? = null): Request {
        val token = accessToken ?: runBlocking { sessionManager.accessToken.first() }
        return request.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
    }

    private suspend fun getNewAccessToken(refreshToken: String): LoginResponse? {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()

        val authApi = retrofit.create(AuthApi::class.java)
        val accessToken = runBlocking { return@runBlocking "Bearer ${sessionManager.accessToken.first()}" }
        val response = authApi.refreshTokens(accessToken = accessToken, refreshToken = refreshToken)

        return if (response.isSuccessful) response.body() else null
    }
}
