package ru.moonlight.domain.cart.interactor

import ru.moonlight.common.ApiResponse
import ru.moonlight.data.api.repository.CartRepository
import javax.inject.Inject

class AddProductToCartInteractor @Inject constructor(
    private val repository: CartRepository,
) {
    suspend operator fun invoke(id: Long, size: String?, count: Int): ApiResponse<Unit> {
        return when (val response = repository.addProductToCart(id, size, count)) {
            is ApiResponse.Error -> ApiResponse.Error(response.msg)
            is ApiResponse.Success -> {
                ApiResponse.Success()
            }
        }
    }

}