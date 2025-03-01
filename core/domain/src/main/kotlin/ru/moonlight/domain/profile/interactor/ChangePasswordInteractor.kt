package ru.moonlight.domain.profile.interactor

import ru.moonlight.data.repository.ProfileRepository
import javax.inject.Inject

class ChangePasswordInteractor @Inject constructor(
    private val repository: ProfileRepository,
) {
    suspend operator fun invoke(oldPassword: String, newPassword: String)
        = repository.changePassword(oldPassword, newPassword)
}