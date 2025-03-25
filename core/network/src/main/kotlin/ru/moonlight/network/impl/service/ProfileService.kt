package ru.moonlight.network.impl.service

import ru.moonlight.common.ApiResponse
import ru.moonlight.network.api.model.profile.ProfileResponse
import ru.moonlight.network.api.service.ProfileService
import ru.moonlight.network.impl.api.ProfileApi
import ru.moonlight.network.impl.utils.ApiCallController
import javax.inject.Inject

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