package ru.moonlight.network.api.service

import kotlinx.coroutines.flow.Flow
import ru.moonlight.common.ApiResponse

interface AuthService {
    val isUserAuthorize: Flow<Boolean>
    suspend fun login(email: String, password: String): ApiResponse<Unit>
    suspend fun requestCode(email: String, name: String, renew: Boolean? = null): ApiResponse<Unit>
    suspend fun confirmCodeCompleteSignUp(code: String, email: String, password: String, name: String, birthDate: String, sex: String): ApiResponse<Unit>
    suspend fun logout()
}