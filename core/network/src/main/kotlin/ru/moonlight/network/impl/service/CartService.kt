package ru.moonlight.network.impl.service

import ru.moonlight.common.ApiResponse
import ru.moonlight.network.api.model.cart.CartItemsResponse
import ru.moonlight.network.api.service.CartService
import ru.moonlight.network.impl.api.CartApi
import ru.moonlight.network.impl.utils.ApiCallController
import javax.inject.Inject

internal class CartApiService @Inject constructor(
    private val api: CartApi,
    private val apiCallController: ApiCallController,
): CartService {
    override suspend fun addItemToCart(id: Long, size: String?, count: Int): ApiResponse<Unit> =
        apiCallController.safeApiCall { api.addItemToCart(id.toString(), size, count.toString()) }

    override suspend fun getCartItems(page: Int): ApiResponse<CartItemsResponse> =
        apiCallController.safeApiCall { api.getCart(page = page) }

    override suspend fun changeCount(id: Long, count: Int): ApiResponse<Unit> =
        apiCallController.safeApiCall { api.changeCount(id = id, count = count) }

    override suspend fun removeItemFromCart(id: Long): ApiResponse<Unit> =
        apiCallController.safeApiCall { api.removeItemFromCart(id) }

}