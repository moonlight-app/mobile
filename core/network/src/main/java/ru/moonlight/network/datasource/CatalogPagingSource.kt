package ru.moonlight.network.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.moonlight.common.ApiResponse
import ru.moonlight.network.api.CatalogApi
import ru.moonlight.network.model.catalog.Product
import ru.moonlight.network.utils.ApiCallController

internal class CatalogPagingSource (
    val api: CatalogApi,
    val apiCallController: ApiCallController,
    val productType: String,
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
            val response = apiCallController.safeApiCall { api.catalogItems(productType = productType, page = position) }
            when (response) {
                is ApiResponse.Error -> {
                    LoadResult.Error(throw Exception(response.msg))
                }
                is ApiResponse.Success -> {
                    LoadResult.Page(
                        data = response.data!!.products,
                        prevKey = if (position == 1) null else (position - 1),
                        nextKey = if (position == response.data!!.productPage.totalPages) null else (position + 1)
                    )
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}