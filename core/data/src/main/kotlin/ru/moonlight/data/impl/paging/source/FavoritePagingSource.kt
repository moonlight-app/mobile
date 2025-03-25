package ru.moonlight.data.impl.paging.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.moonlight.common.ApiResponse
import ru.moonlight.network.api.model.favorite.FavoriteProducts
import ru.moonlight.network.api.service.FavoritesService

class FavoritePagingSource(
    private val service: FavoritesService,
): PagingSource<Int, FavoriteProducts>() {

    override fun getRefreshKey(state: PagingState<Int, FavoriteProducts>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FavoriteProducts> {
        val position = params.key ?: 1

        return try {
            when (val response = service.getFavorites( page = position)) {
                is ApiResponse.Error -> {
                    LoadResult.Error(throw Exception(response.msg))
                }
                is ApiResponse.Success -> {
                    LoadResult.Page(
                        data = response.data?.favoriteProducts ?: emptyList(),
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