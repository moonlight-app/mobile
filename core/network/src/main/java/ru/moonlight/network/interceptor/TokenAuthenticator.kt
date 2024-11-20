package ru.moonlight.network.interceptor

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.moonlight.network.BuildConfig
import ru.moonlight.network.api.AuthApi
import ru.moonlight.network.model.LoginResponse
import ru.moonlight.network.utils.SessionManager
import javax.inject.Inject

internal class TokenAuthenticator @Inject constructor(
    private val sessionManager: SessionManager,
): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val token: String = runBlocking {
            sessionManager.refreshToken.first()
        }
        return runBlocking {
            val newToken = getNewToken(token)

            if (!newToken.isSuccessful || newToken.body() == null) {
                sessionManager.clearTokens()
            }

            newToken.body()?.let { body ->
                sessionManager.saveTokens(
                    accessPair = Pair(body.accessToken, body.accessTokenExpiresIn),
                    refreshPair = Pair(body.refreshToken, body.refreshTokenExpiresIn),
                )
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${body.accessToken}")
                    .build()
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String): retrofit2.Response<LoginResponse> {
        val loggingInterceptor = HttpLoggingInterceptor()
            .apply {
                if (BuildConfig.DEBUG) {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(Json.asConverterFactory(contentType = "application/json".toMediaType()))
            .client(okHttpClient)
            .build()
        val service = retrofit.create(AuthApi::class.java)

        return service.refreshTokens(refreshToken = refreshToken)
    }
}