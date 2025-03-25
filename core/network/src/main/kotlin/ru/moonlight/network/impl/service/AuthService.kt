package ru.moonlight.network.impl.service

import kotlinx.coroutines.flow.Flow
import ru.moonlight.common.ApiResponse
import ru.moonlight.network.api.service.AuthService
import ru.moonlight.network.impl.api.AuthApi
import ru.moonlight.network.impl.utils.ApiCall
import ru.moonlight.network.impl.utils.ApiCallController
import ru.moonlight.network.impl.utils.SessionManager
import javax.inject.Inject

internal class AuthApiService @Inject constructor(
    private val api: AuthApi,
    private val sessionManager: SessionManager,
    private val apiCallController: ApiCallController,
): AuthService {
    private var xProofKey = ""

    override val isUserAuthorize: Flow<Boolean>
        get() = sessionManager.isUserAuthorized

    override suspend fun login(email: String, password: String): ApiResponse<Unit> {
        val result = apiCallController.safeApiCall { api.signIn(email = email, password = password) }
        return when (result) {
            is ApiResponse.Error -> ApiResponse.Error(msg = result.msg, code = result.code)
            is ApiResponse.Success -> {
                sessionManager.saveTokens(
                    accessPair = Pair(first = result.data!!.accessToken, second = result.data!!.accessTokenExpiresIn),
                    refreshPair = Pair(first = result.data!!.refreshToken, second = result.data!!.refreshTokenExpiresIn),
                )
                ApiResponse.Success(code = result.code)
            }
        }
    }

    override suspend fun requestCode(email: String, name: String, renew: Boolean?): ApiResponse<Unit> {
        val result = apiCallController.safeApiCallWithHeader { api.requestCode(email = email, name = name, renew = renew) }
        return when(result) {
            is ApiCall.Error -> ApiResponse.Error(msg = result.msg, code = result.code)
            is ApiCall.Success -> {
                xProofKey = result.headers!!.getValue("X-Proof-Key")
                ApiResponse.Success(code = result.code)
            }
        }
    }

    override suspend fun confirmCodeCompleteSignUp(
        code: String,
        email: String,
        password: String,
        name: String,
        birthDate: String,
        sex: String
    ): ApiResponse<Unit> {
        return when(val confirmCodeResponse = apiCallController.safeApiCall { api.confirmCode(key = xProofKey, email = email, code = code) }) {
            is ApiResponse.Success -> {
                when (val result = apiCallController.safeApiCall { api.complete(
                    key = xProofKey,
                    email = email,
                    password = password,
                    name = name,
                    birthDate = birthDate,
                    sex = sex
                )}) {
                    is ApiResponse.Error -> ApiResponse.Error(msg = result.msg)
                    is ApiResponse.Success -> {
                        sessionManager.saveTokens(
                            accessPair = Pair(first = result.data!!.accessToken, second = result.data!!.accessTokenExpiresIn),
                            refreshPair = Pair(first = result.data!!.refreshToken, second = result.data!!.refreshTokenExpiresIn),
                        )
                        ApiResponse.Success()
                    }
                }

            }
            is ApiResponse.Error -> ApiResponse.Error(msg = confirmCodeResponse.msg)
        }
    }

    override suspend fun logout() {
        sessionManager.clearTokens()
    }

}