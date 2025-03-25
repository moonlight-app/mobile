package ru.moonlight.network.impl.service

import ru.moonlight.common.ApiResponse
import ru.moonlight.network.api.service.OrderService
import ru.moonlight.network.impl.api.OrderApi
import ru.moonlight.network.impl.utils.ApiCallController
import javax.inject.Inject

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