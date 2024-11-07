package ru.moonlight.network.utils

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface TokenManager {
    val isUserAuthorized: Flow<Boolean>
    val accessToken: Flow<String>
    val refreshToken: Flow<String>
    suspend fun saveAccessToken(token: String)
    suspend fun saveRefreshToken(token: String)
    suspend fun clearTokens()
}

internal class TokenManagerImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
): TokenManager {
    override val isUserAuthorized: Flow<Boolean> = authDataSource.isUserAuthorizedFlow
    override val accessToken: Flow<String> = authDataSource.accessTokenFlow
    override val refreshToken: Flow<String> = authDataSource.refreshTokenFlow

    override suspend fun saveAccessToken(token: String) {
        authDataSource.saveAccessToken(token)
    }

    override suspend fun saveRefreshToken(token: String) {
        authDataSource.saveRefreshToken(token)
    }

    override suspend fun clearTokens() {
        authDataSource.clearAccessToken()
        authDataSource.clearRefreshToken()
    }
}