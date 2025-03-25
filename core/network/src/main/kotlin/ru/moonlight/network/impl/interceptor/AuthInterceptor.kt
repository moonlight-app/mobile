package ru.moonlight.network.impl.interceptor

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import ru.moonlight.network.impl.utils.SessionManager
import javax.inject.Inject

internal class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val accessToken: String = runBlocking {
            sessionManager.accessToken.first()
        }
        val request = chain.request().newBuilder()

        if (accessToken.isNotEmpty()) {
            request.addHeader("Authorization", "Bearer $accessToken")
        }

        return chain.proceed(request.build())
    }
}