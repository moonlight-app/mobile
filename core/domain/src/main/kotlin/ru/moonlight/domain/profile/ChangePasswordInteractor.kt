package ru.moonlight.domain.profile

import ru.moonlight.data.repository.ProfileRepository
import javax.inject.Inject

class ChangePasswordInteractor @Inject constructor(
    private val repository: ProfileRepository,
) {
    suspend fun invoke(oldPassword: String, newPassword: String)
        = repository.changePassword(oldPassword, newPassword)
}