package ru.moonlight.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.moonlight.network.model.ProfileResponse
import ru.moonlight.network.service.ProfileService
import ru.moonlight.network.utils.ApiResponse
import javax.inject.Inject

interface ProfileRepository {
    suspend fun getProfile(): ApiResponse<ProfileResponse>
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

internal class ProfileRepositoryImpl @Inject constructor(
    private val service: ProfileService,
    private val dispatcherIO: CoroutineDispatcher,
): ProfileRepository {

    override suspend fun getProfile(): ApiResponse<ProfileResponse> =
        withContext(dispatcherIO) {
            when (val response = service.getProfile()) {
                is ApiResponse.Error -> ApiResponse.Error(msg = response.msg)
                is ApiResponse.Success -> ApiResponse.Success(data = response.data)
            }
        }

    override suspend fun changeProfile(
        name: String?,
        gender: String?,
        birthDate: String?,
    ): ApiResponse<Unit> =
        withContext(dispatcherIO) {
            service.updateProfile(name, gender, birthDate)
        }

    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
    ): ApiResponse<Unit> =
        withContext(dispatcherIO) {
            service.changePassword(currentPassword, newPassword)
        }

    override suspend fun deleteProfile(): ApiResponse<Unit> =
        withContext(dispatcherIO) {
            service.deleteProfile()
        }

}