package ru.moonlight.network.utils

import kotlinx.coroutines.flow.Flow
import ru.moonlight.network.datasource.AuthDataSource
import javax.inject.Inject

internal interface SessionManager {
    val isUserAuthorized: Flow<Boolean>
    val accessToken: Flow<String>
    val refreshToken: Flow<String>
    suspend fun saveTokens(accessPair: Pair<String, Long>, refreshPair: Pair<String, Long>)
    suspend fun clearTokens()
}

internal class SessionManagerImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
): SessionManager {
    override val isUserAuthorized: Flow<Boolean> = authDataSource.isUserAuthorizedFlow
    override val accessToken: Flow<String> = authDataSource.accessTokenFlow
    override val refreshToken: Flow<String> = authDataSource.refreshTokenFlow

    override suspend fun saveTokens(accessPair: Pair<String, Long>, refreshPair: Pair<String, Long>) {
        authDataSource.saveAccessToken(accessPair.first)
        authDataSource.saveAccessTokenExpiresIn(accessPair.second)
        authDataSource.saveRefreshToken(refreshPair.first)
        authDataSource.saveRefreshTokenExpiresIn(refreshPair.second)
    }

    override suspend fun clearTokens() {
        authDataSource.clearAccessToken()
        authDataSource.clearRefreshToken()
    }


}