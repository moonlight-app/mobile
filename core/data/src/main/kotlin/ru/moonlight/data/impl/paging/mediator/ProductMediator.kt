package ru.moonlight.data.impl.paging.mediator

import android.net.http.HttpException
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import ru.moonlight.common.ApiResponse
import ru.moonlight.data.impl.mapper.mapToEntity
import ru.moonlight.database.impl.MoonlightDatabase
import ru.moonlight.database.impl.catalog.ProductEntity
import ru.moonlight.network.api.service.CatalogService
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ProductMediator(
    private val service: CatalogService,
    private val database: MoonlightDatabase,
    private val category: String,
    private val sortBy: String?,
    private val minPrice: Float?,
    private val maxPrice: Float?,
    private val sizes: List<Float>?,
    private val audiences: Int?,
    private val materials: Int?,
    private val treasures: Int?,
): RemoteMediator<Int, ProductEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductEntity>
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
                    else { (database.productDao.getCountOfRows() / state.config.pageSize) + 1 }
                }
            }

            val response = service.getProductItems(
                page = currentPage,
                category = category,
                sortBy = sortBy,
                minPrice = minPrice,
                maxPrice = maxPrice,
                sizes = sizes,
                audiences = audiences,
                materials = materials,
                treasures = treasures,
            )

            when (response) {
                is ApiResponse.Error -> MediatorResult.Error(Exception(response.msg))
                is ApiResponse.Success -> {
                    if(loadType == LoadType.REFRESH) {
                        database.productDao.clearAll()
                    }
                    val productEntities = response.data?.products ?: emptyList()

                    if (productEntities.isEmpty()) {
                        database.productDao.clearAll()
                    } else {
                        database.productDao.upsertAll(productEntities.map { it.mapToEntity() })
                    }

                    MediatorResult.Success(
                        endOfPaginationReached = (response.data?.productPage?.totalPages ?: 1) == currentPage
                    )
                }
            }
        } catch(e: IOException) {
            MediatorResult.Error(e)
        } catch(e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}