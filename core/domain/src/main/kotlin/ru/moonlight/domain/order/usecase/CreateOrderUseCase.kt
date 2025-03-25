package ru.moonlight.domain.order.usecase

import ru.moonlight.common.ApiResponse
import ru.moonlight.data.api.repository.CartRepository
import ru.moonlight.data.api.repository.OrderRepository
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
) {
    suspend operator fun invoke(): ApiResponse<Unit> {
        val cartItemIds = cartRepository.getIdsOfAvailableProductsInCart()
        return when (val response = orderRepository.addOrder(cartItemIds = cartItemIds)) {
            is ApiResponse.Error -> ApiResponse.Error(response.msg)
            is ApiResponse.Success -> {
                cartItemIds.forEach { id ->
                    cartRepository.deleteProductFromCartDatabase(id)
                }
                ApiResponse.Success()
            }
        }
    }

}