package ru.moonlight.network.service

import ru.moonlight.network.api.ProfileApi
import ru.moonlight.network.model.ProfileResponse
import ru.moonlight.network.utils.ApiCallController
import ru.moonlight.network.utils.ApiResponse
import javax.inject.Inject

interface ProfileService {
    suspend fun getProfile(): ApiResponse<ProfileResponse>
    suspend fun updateProfile(name: String?, sex: String?, birthDate: String?): ApiResponse<Unit>
    suspend fun changePassword(currentPassword: String, newPassword: String): ApiResponse<Unit>
    suspend fun deleteProfile(): ApiResponse<Unit>
}

internal class ProfileApiService @Inject constructor(
    private val api: ProfileApi,
    private val apiCallController: ApiCallController,
): ProfileService {
    override suspend fun getProfile(): ApiResponse<ProfileResponse>
        = apiCallController.safeApiCall { api.getProfile() }

    override suspend fun updateProfile(
        name: String?,
        sex: String?,
        birthDate: String?,
    ): ApiResponse<Unit> = apiCallController.safeApiCallWithoutBody { api.updateProfile(name, sex, birthDate) }

    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
    ): ApiResponse<Unit> = apiCallController.safeApiCallWithoutBody {
        api.changePassword(currentPassword, newPassword)
    }

    override suspend fun deleteProfile(): ApiResponse<Unit>
        = apiCallController.safeApiCallWithoutBody { api.deleteProfile() }

}