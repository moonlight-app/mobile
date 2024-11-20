package ru.moonlight.network.datasource

import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    val isUserAuthorizedFlow: Flow<Boolean>
    val accessTokenFlow: Flow<String>
    val accessTokenExpiresInFlow: Flow<Long>
    val refreshTokenFlow: Flow<String>
    val refreshTokenExpiresInFlow: Flow<Long>
    suspend fun saveAccessToken(token: String)
    suspend fun saveAccessTokenExpiresIn(time: Long)
    suspend fun saveRefreshToken(token: String)
    suspend fun saveRefreshTokenExpiresIn(time: Long)
    suspend fun clearAccessToken()
    suspend fun clearRefreshToken()
}