package ru.moonlight.data.paging.mediator

import android.net.http.HttpException
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import ru.moonlight.common.ApiResponse
import ru.moonlight.data.mapper.mapToLocalCartEntity
import ru.moonlight.data.mapper.mapToMediatorCartEntity
import ru.moonlight.database.MoonlightDatabase
import ru.moonlight.database.cart.CartForMediatorEntity
import ru.moonlight.network.model.cart.CartItem
import ru.moonlight.network.service.CartService
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CartMediator(
    private val service: CartService,
    private val database: MoonlightDatabase,
): RemoteMediator<Int, CartForMediatorEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CartForMediatorEntity>
    ): MediatorResult {
        return try {
            val currentPage: Int = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if(lastItem == null) { 1 }
                    else { (database.mediatorCartDao.getCountOfRows() / state.config.pageSize) + 1 }
                }
            }

            when (val response = service.getCartItems(page = currentPage)) {
                is ApiResponse.Error -> MediatorResult.Error(Exception(response.msg))
                is ApiResponse.Success -> {
                    if(loadType == LoadType.REFRESH) {
                        database.mediatorCartDao.clearAll()
                    }

                    val productsInCart = response.data?.productsInCart ?: emptyList()

                    if (productsInCart.isEmpty()) {
                        database.mediatorCartDao.clearAll()
                        database.localCartDao.clearAll()
                    } else {
                        insertIntoMediatorCartDaoAndSynchronizeWithLocalCart(products = productsInCart)
                    }

                    MediatorResult.Success(
                        endOfPaginationReached = if (response.data != null) response.data?.cartPage?.totalPages == currentPage else true
                    )
                }
            }
        } catch(e: IOException) {
            MediatorResult.Error(e)
        } catch(e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun insertIntoMediatorCartDaoAndSynchronizeWithLocalCart(products: List<CartItem>) {
        database.withTransaction {
            database.mediatorCartDao.insertAll(products = products.map { it.mapToMediatorCartEntity() })
            database.localCartDao.insertNewItemsAndDeleteOldItems(products = products.map { it.mapToLocalCartEntity() })
        }
    }

}