package ru.moonlight.data.repository

import kotlinx.coroutines.flow.Flow
import ru.moonlight.network.service.AuthService
import ru.moonlight.network.utils.TokenManager
import javax.inject.Inject

interface AuthRepository {
    suspend fun login(login: String, password: String)
    suspend fun register(login: String, password: String, name: String, sex: String, age: Int)
    suspend fun logout()
    val isUserAuthorized: Flow<Boolean>
}

internal class AuthRepositoryImpl @Inject constructor(
    private val api: AuthService,
    private val tokenManager: TokenManager,
): AuthRepository {
    override suspend fun login(login: String, password: String) {
        api.login(login, password)
    }

    override suspend fun register(
        login: String,
        password: String,
        name: String,
        sex: String,
        age: Int
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        tokenManager.clearTokens()
    }

    override val isUserAuthorized: Flow<Boolean> = tokenManager.isUserAuthorized

}