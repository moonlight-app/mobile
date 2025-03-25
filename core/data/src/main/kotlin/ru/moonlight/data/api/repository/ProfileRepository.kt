package ru.moonlight.data.api.repository

import ru.moonlight.common.ApiResponse
import ru.moonlight.domain_model.profile.ProfileDomainModel

interface ProfileRepository {
    suspend fun getProfile(): ApiResponse<ProfileDomainModel>
    suspend fun changeProfile(
        name: String?,
        gender: String?,
        birthDate: String?,
    ): ApiResponse<Unit>
    suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
    ): ApiResponse<Unit>
    suspend fun deleteProfile(): ApiResponse<Unit>
}