package ru.moonlight.data.api.repository

import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import ru.moonlight.common.ApiResponse
import ru.moonlight.domain_model.cart.CartItemDomainModel

interface CartRepository {
    suspend fun addProductToCart(id: Long, size: String?, count: Int): ApiResponse<Unit>
    suspend fun deleteProductFromCartApiAndDatabase(id: Long): ApiResponse<Unit>
    suspend fun deleteProductFromCartDatabase(id: Long)
    suspend fun getIdsOfAvailableProductsInCart(): List<Long>
    fun getProductsInCartPagingData(): Flow<PagingData<CartItemDomainModel>>
    suspend fun updateChoseItemStatus(id: Long, status: Boolean)
    suspend fun updateChoseItemStatusForAllItems(status: Boolean)
    suspend fun updateCountOfProducts(itemId: Long, count: Int)
    fun getProductsInCartPagingData(scope: CoroutineScope): Flow<PagingData<CartItemDomainModel>>
}