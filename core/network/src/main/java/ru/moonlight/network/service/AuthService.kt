package ru.moonlight.network.service

import ru.moonlight.network.utils.TokenManager
import javax.inject.Inject
import kotlin.random.Random

class AuthService @Inject constructor(
    private val tokenManager: TokenManager,
) {
    suspend fun login(login: String, password: String) {
        val accessToken = "$login + $password accessToken" + repeat(20) {Random(5).nextInt(1, 10).toString()}
        val refreshToken = "$login + $password refreshToken" + repeat(20) {Random(5).nextInt(1, 10).toString()}
        tokenManager.saveAccessToken(accessToken)
        tokenManager.saveRefreshToken(refreshToken)
    }

}