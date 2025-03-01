package ru.moonlight.network.service

import ru.moonlight.common.ApiResponse
import ru.moonlight.network.api.OrderApi
import ru.moonlight.network.model.order.OrderResponse
import ru.moonlight.network.utils.ApiCallController
import javax.inject.Inject

interface OrderService {
    suspend fun getOrders(page: Int): ApiResponse<OrderResponse>
    suspend fun addOrder(cartItemIds: List<Long>): ApiResponse<Unit>
}

internal class OrderApiService @Inject constructor(
    private val api: OrderApi,
    private val apiCallController: ApiCallController,
): OrderService {
    override suspend fun getOrders(page: Int) = apiCallController.safeApiCall {
        api.getOrders(page)
    }

    override suspend fun addOrder(cartItemIds: List<Long>): ApiResponse<Unit> =
        apiCallController.safeApiCall {
            api.createOrder(itemIds = cartItemIds)
        }
}