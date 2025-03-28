package ru.moonlight.domain.profile.interactor

import ru.moonlight.data.api.repository.ProfileRepository
import javax.inject.Inject

class UpdateProfileInteractor @Inject constructor(
    private val repository: ProfileRepository,
) {
    suspend fun invoke(name: String?, gender: String?, birthDate: String?)
        = repository.changeProfile(name, gender, birthDate)
}