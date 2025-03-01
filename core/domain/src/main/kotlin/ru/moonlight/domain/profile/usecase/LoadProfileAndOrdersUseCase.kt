package ru.moonlight.domain.profile.usecase

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import ru.moonlight.data.repository.ProfileRepository
import ru.moonlight.domain.order.interactor.GetOrdersInteractor
import javax.inject.Inject

class LoadProfileAndOrdersUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val getOrdersInteractor: GetOrdersInteractor,
) {
    suspend operator fun invoke() = coroutineScope {
        val profileData = async {
            profileRepository.getProfile()
        }

        val ordersFlow = getOrdersInteractor()

        return@coroutineScope Pair(profileData.await(), ordersFlow)
    }
}