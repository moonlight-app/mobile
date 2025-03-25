package ru.moonlight.data.api.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.moonlight.common.ApiResponse
import ru.moonlight.domain_model.order.OrdersDomainModel

interface OrderRepository {
    fun getOrders(): Flow<PagingData<OrdersDomainModel>>
    suspend fun addOrder(cartItemIds: List<Long>): ApiResponse<Unit>
}