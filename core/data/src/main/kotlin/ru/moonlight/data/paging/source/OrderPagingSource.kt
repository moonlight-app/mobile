package ru.moonlight.data.paging.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.moonlight.common.ApiResponse
import ru.moonlight.network.model.order.OrderContent
import ru.moonlight.network.service.OrderService

class OrderPagingSource(
    val service: OrderService,
): PagingSource<Int, OrderContent>() {

    override fun getRefreshKey(state: PagingState<Int, OrderContent>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OrderContent> {
        val position = params.key ?: 1

        return try {
            when (val response = service.getOrders(position)) {
                is ApiResponse.Error -> LoadResult.Error(throw Exception(response.msg))
                is ApiResponse.Success -> {
                    val prevKey = if (position == 1) null else (position - 1)
                    val nextKey = if (position == (response.data?.orderPage?.totalPages ?: 1)) null else (position + 1)

                    LoadResult.Page(
                        data = response.data?.orders ?: emptyList(),
                        prevKey = prevKey,
                        nextKey = nextKey,
                    )
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}