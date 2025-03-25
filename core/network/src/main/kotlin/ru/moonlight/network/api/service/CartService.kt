package ru.moonlight.network.api.service

import ru.moonlight.common.ApiResponse
import ru.moonlight.network.api.model.cart.CartItemsResponse

interface CartService {
    suspend fun addItemToCart(id: Long, size: String?, count: Int): ApiResponse<Unit>
    suspend fun getCartItems(page: Int): ApiResponse<CartItemsResponse>
    suspend fun changeCount(id: Long, count: Int): ApiResponse<Unit>
    suspend fun removeItemFromCart(id: Long): ApiResponse<Unit>
}