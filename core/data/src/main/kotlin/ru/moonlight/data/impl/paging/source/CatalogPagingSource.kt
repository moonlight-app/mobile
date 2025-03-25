package ru.moonlight.data.impl.paging.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.moonlight.common.ApiResponse
import ru.moonlight.network.api.model.catalog.Product
import ru.moonlight.network.api.service.CatalogService

class CatalogPagingSource (
    val service: CatalogService,
    val productType: String,
    val sortBy: String?,
    val minPrice: Float?,
    val maxPrice: Float?,
    val sizes: List<Float>?,
    val audiences: Int?,
    val materials: Int?,
    val treasures: Int?,
): PagingSource<Int, Product>() {

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val position = params.key ?: 1

        return try {
            val response = service.getProductItems(
                category = productType,
                sortBy = sortBy,
                minPrice = minPrice,
                maxPrice = maxPrice,
                sizes = sizes,
                audiences = audiences,
                materials = materials,
                treasures = treasures,
                page = position,
            )

            when (response) {
                is ApiResponse.Error -> {
                    LoadResult.Error(throw Exception(response.msg))
                }
                is ApiResponse.Success -> {
                    LoadResult.Page(
                        data = response.data?.products ?: emptyList(),
                        prevKey = if (position == 1) null else (position - 1),
                        nextKey = if (position == (response.data?.productPage?.totalPages ?: 1)) null else (position + 1)
                    )
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}