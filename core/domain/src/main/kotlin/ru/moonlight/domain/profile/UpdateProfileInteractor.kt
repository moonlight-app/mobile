package ru.moonlight.domain.profile

import ru.moonlight.data.repository.ProfileRepository
import javax.inject.Inject

class UpdateProfileInteractor @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend fun invoke(name: String?, gender: String?, birthDate: String?)
        = repository.changeProfile(name, gender, birthDate)
}