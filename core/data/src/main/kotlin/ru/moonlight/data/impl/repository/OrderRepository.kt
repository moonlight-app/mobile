package ru.moonlight.data.impl.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.moonlight.common.ApiResponse
import ru.moonlight.common.di.Dispatcher
import ru.moonlight.common.di.MoonlightDispatchers
import ru.moonlight.data.api.repository.OrderRepository
import ru.moonlight.data.impl.mapper.mapToDomain
import ru.moonlight.data.impl.paging.source.OrderPagingSource
import ru.moonlight.domain_model.order.OrdersDomainModel
import ru.moonlight.network.api.service.OrderService
import javax.inject.Inject

internal class OrderRepositoryImpl @Inject constructor(
    private val service: OrderService,
    @Dispatcher(MoonlightDispatchers.IO) private val dispatcherIO: CoroutineDispatcher,
): OrderRepository {
    override fun getOrders(): Flow<PagingData<OrdersDomainModel>> =
        Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { OrderPagingSource(service) }
        )
            .flow
            .flowOn(dispatcherIO)
            .map { pagingData ->
                pagingData.map { it.mapToDomain() }
            }

    override suspend fun addOrder(cartItemIds: List<Long>): ApiResponse<Unit> =
        withContext(dispatcherIO) {
            service.addOrder(cartItemIds = cartItemIds)
        }

}