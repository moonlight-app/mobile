package ru.moonlight.network.api.service

import ru.moonlight.common.ApiResponse
import ru.moonlight.network.api.model.profile.ProfileResponse

interface ProfileService {
    suspend fun getProfile(): ApiResponse<ProfileResponse>
    suspend fun updateProfile(name: String?, sex: String?, birthDate: String?): ApiResponse<Unit>
    suspend fun changePassword(currentPassword: String, newPassword: String): ApiResponse<Unit>
    suspend fun deleteProfile(): ApiResponse<Unit>
}