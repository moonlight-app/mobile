package ru.moonlight.network.service

import ru.moonlight.common.ApiResponse
import ru.moonlight.network.api.CartApi
import ru.moonlight.network.model.cart.CartItemsResponse
import ru.moonlight.network.utils.ApiCallController
import javax.inject.Inject

interface CartService {
    suspend fun addItemToCart(id: Long, size: String?, count: Int): ApiResponse<Unit>
    suspend fun getCartItems(page: Int): ApiResponse<CartItemsResponse>
    suspend fun changeCount(id: Long, count: Int): ApiResponse<Unit>
    suspend fun removeItemFromCart(id: Long): ApiResponse<Unit>
}

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