package ru.moonlight.data.api.repository

import kotlinx.coroutines.flow.Flow
import ru.moonlight.common.ApiResponse

interface AuthRepository {
    val isUserAuthorized: Flow<Boolean>
    suspend fun login(login: String, password: String): ApiResponse<Unit>
    suspend fun requestCode(email: String, name: String, renew: Boolean? = null): ApiResponse<Unit>
    suspend fun confirmCode(code: String, email: String, password: String, name: String, birthDate: String, sex: String): ApiResponse<Unit>
    suspend fun logout()
}