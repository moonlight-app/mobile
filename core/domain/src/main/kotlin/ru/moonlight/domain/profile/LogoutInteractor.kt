package ru.moonlight.domain.profile

import ru.moonlight.data.repository.AuthRepository
import javax.inject.Inject

class LogoutInteractor @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend fun invoke() = repository.logout()
}