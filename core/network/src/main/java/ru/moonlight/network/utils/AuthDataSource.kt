package ru.moonlight.network.utils

import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    val isUserAuthorizedFlow: Flow<Boolean>
    val accessTokenFlow: Flow<String>
    val refreshTokenFlow: Flow<String>
    suspend fun saveAccessToken(token: String)
    suspend fun saveRefreshToken(token: String)
    suspend fun clearAccessToken()
    suspend fun clearRefreshToken()
}