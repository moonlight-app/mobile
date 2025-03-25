package ru.moonlight.network.api.service

import ru.moonlight.common.ApiResponse
import ru.moonlight.network.api.model.order.OrderResponse

interface OrderService {
    suspend fun getOrders(page: Int): ApiResponse<OrderResponse>
    suspend fun addOrder(cartItemIds: List<Long>): ApiResponse<Unit>
}