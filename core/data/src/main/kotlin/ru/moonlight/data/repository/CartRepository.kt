package ru.moonlight.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.moonlight.common.ApiResponse
import ru.moonlight.common.di.Dispatcher
import ru.moonlight.common.di.MoonlightDispatchers
import ru.moonlight.data.mapper.mapToDomain
import ru.moonlight.data.paging.mediator.CartMediator
import ru.moonlight.database.MoonlightDatabase
import ru.moonlight.database.cart.CartForMediatorEntity
import ru.moonlight.domain_model.cart.CartItemDomainModel
import ru.moonlight.network.service.CartService
import javax.inject.Inject

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

internal class CartRepositoryImpl @Inject constructor(
    private val service: CartService,
    private val database: MoonlightDatabase,
    @Dispatcher(MoonlightDispatchers.IO) private val dispatcherIO: CoroutineDispatcher
): CartRepository {
    override suspend fun addProductToCart(id: Long, size: String?, count: Int): ApiResponse<Unit> = withContext(dispatcherIO) {
        service.addItemToCart(id = id, size = size, count = count)
    }

    override suspend fun deleteProductFromCartDatabase(id: Long) = withContext(dispatcherIO) {
        database.mediatorCartDao.deleteItem(id)
    }

    override suspend fun getIdsOfAvailableProductsInCart(): List<Long> = withContext(dispatcherIO) {
        database.localCartDao.getAllAvailableItemIds()
    }

    override suspend fun deleteProductFromCartApiAndDatabase(id: Long): ApiResponse<Unit> = withContext(dispatcherIO) {
        return@withContext when (val response = service.removeItemFromCart(id = id)) {
            is ApiResponse.Error -> ApiResponse.Error(response.msg)
            is ApiResponse.Success -> {
                database.localCartDao.deleteItem(id)
                database.mediatorCartDao.deleteItem(id)
                ApiResponse.Success()
            }
        }
    }

    override suspend fun updateChoseItemStatus(id: Long, status: Boolean) = withContext(dispatcherIO) {
        database.localCartDao.updateChosenItemStatus(id = id, chosen = status)
    }

    override suspend fun updateChoseItemStatusForAllItems(status: Boolean) = withContext(dispatcherIO) {
        database.localCartDao.updateChosenItemStatusForAllItems(chosen = status)
    }

    override suspend fun updateCountOfProducts(itemId: Long, count: Int): Unit = withContext(dispatcherIO) {
        database.localCartDao.updateItemCount(id = itemId, count = count)
        service.changeCount(id = itemId, count = count)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getProductsInCartPagingData(): Flow<PagingData<CartItemDomainModel>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = CartMediator(service = service, database = database),
            pagingSourceFactory = {
                database.mediatorCartDao.cartPagingSource()
            }
        ).flow
            .flowOn(dispatcherIO)
            .map { data: PagingData<CartForMediatorEntity> ->
                data.map { cartItem ->
                    cartItem.mapToDomain()
                }
            }

        return combine(
            pager,
            database.localCartDao.getAllItems(),
        ) { remoteCart, localCart ->
            remoteCart.map { cartItem ->
                cartItem.copy(
                    count = localCart.firstOrNull { it.itemId == cartItem.itemId }?.count ?: 0,
                    isChosen = localCart.firstOrNull { it.itemId == cartItem.itemId }?.chosen ?: false
                )
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getProductsInCartPagingData(scope: CoroutineScope): Flow<PagingData<CartItemDomainModel>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = CartMediator(service = service, database = database),
            pagingSourceFactory = {
                database.mediatorCartDao.cartPagingSource()
            }
        ).flow
            .flowOn(dispatcherIO)
            .map { data ->
                data.map { it.mapToDomain() }
            }
            .cachedIn(scope)

        return combine(pager, database.localCartDao.getAllItems()) { remoteCart, localCart ->
            val localMap = localCart.associateBy { it.itemId }
            remoteCart.map { cartItem ->
                val local = localMap[cartItem.itemId]
                cartItem.copy(
                    count = local?.count ?: 0,
                    isChosen = local?.chosen ?: false,
                    isFavorite = local?.isFavorite ?: false,
                )
            }
        }
    }

}