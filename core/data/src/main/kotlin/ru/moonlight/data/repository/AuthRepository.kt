package ru.moonlight.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.moonlight.network.service.AuthService
import ru.moonlight.common.ApiResponse
import javax.inject.Inject

interface AuthRepository {
    val isUserAuthorized: Flow<Boolean>
    suspend fun login(login: String, password: String): ApiResponse<Unit>
    suspend fun requestCode(email: String, name: String, renew: Boolean? = null): ApiResponse<Unit>
    suspend fun confirmCode(code: String, email: String, password: String, name: String, birthDate: String, sex: String): ApiResponse<Unit>
    suspend fun logout()
}

internal class AuthRepositoryImpl @Inject constructor(
    private val service: AuthService,
    private val dispatcherIO: CoroutineDispatcher,
): AuthRepository {
    override val isUserAuthorized: Flow<Boolean>
        get() = service.isUserAuthorize

    override suspend fun login(login: String, password: String): ApiResponse<Unit> =
        withContext(dispatcherIO) {
            service.login(email = login, password = password)
        }

    override suspend fun requestCode(email: String, name: String, renew: Boolean?): ApiResponse<Unit> =
        withContext(dispatcherIO) {
            service.requestCode(email = email, name = name, renew = renew)
        }

    override suspend fun confirmCode(
        code: String,
        email: String,
        password: String,
        name: String,
        birthDate: String,
        sex: String,
    ): ApiResponse<Unit> =
        withContext(dispatcherIO) {
            service.confirmCodeCompleteSignUp(code = code, email = email, password = password, name = name, birthDate = birthDate, sex = sex)
        }

    override suspend fun logout() {
        service.logout()
    }

}