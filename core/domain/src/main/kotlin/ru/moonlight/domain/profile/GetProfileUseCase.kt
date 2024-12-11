package ru.moonlight.domain.profile

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import ru.moonlight.common.ApiResponse
import ru.moonlight.data.repository.ProfileRepository
import ru.moonlight.domain_model.order.OrdersDomainModel
import ru.moonlight.domain_model.profile.ProfileDomainModel
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
//    private val orderRepository: OrderRepository, //TODO раскоментить, когда появится orderRepository
) {
    suspend fun invoke(): ApiResponse<ProfileDomainModel> = coroutineScope {
        val profile = async { return@async profileRepository.getProfile() }.await()
        val orders = async {
            delay(500)
            return@async ApiResponse.Success(
                listOf(OrdersDomainModel("1 order ffffffffffffffffffffffffffffffffffffffffffffffffffffff", "link", "New"), OrdersDomainModel("2 order", "link", "бла"), OrdersDomainModel("3 order", "link", "New"), OrdersDomainModel("4 order", "link", "New"), OrdersDomainModel("5 order", "link", "New"))
            )
        }.await()

        if (profile is ApiResponse.Success && orders is ApiResponse.Success) {
            return@coroutineScope ApiResponse.Success(
                ProfileDomainModel(
                    email = profile.data!!.email,
                    name = profile.data!!.name,
                    gender = profile.data!!.gender,
                    birthDate = profile.data!!.birthDate,
                    orders = orders.data!!
                )
            )
        } else
            return@coroutineScope ApiResponse.Error(profile.msg ?: orders.msg ?: "Unknown error")
    }

}