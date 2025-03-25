package ru.moonlight.data.impl.paging.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.moonlight.common.ApiResponse
import ru.moonlight.network.api.model.cart.CartItem
import ru.moonlight.network.api.service.CartService

class CartPagingSource(
    private val service: CartService,
    private val saveCartItemsInDatabase: suspend (List<CartItem>) -> Unit,
    private val clearCartDatabase: suspend () -> Unit,
): PagingSource<Int, CartItem>() {

    override fun getRefreshKey(state: PagingState<Int, CartItem>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CartItem> {
        val position = params.key ?: 1

        return try {
            val response = service.getCartItems(page = position)

            when (response) {
                is ApiResponse.Error -> {
                    LoadResult.Error(throw Exception(response.msg))
                }

                is ApiResponse.Success -> {
                    val productsInCart = response.data?.productsInCart ?: emptyList()

                    if (productsInCart.isEmpty()) {
                        clearCartDatabase()
                    } else {
                        saveCartItemsInDatabase(productsInCart)
                    }

                    LoadResult.Page(
                        data = productsInCart,
                        prevKey = if (position == 1) null else (position - 1),
                        nextKey = if (position == (response.data?.cartPage?.totalPages ?: 1)) null else (position + 1)
                    )
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}