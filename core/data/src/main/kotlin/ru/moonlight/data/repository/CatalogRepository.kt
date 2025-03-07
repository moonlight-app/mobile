package ru.moonlight.data.repository

import androidx.paging.ExperimentalPagingApi
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
import ru.moonlight.data.mapper.mapToDomain
import ru.moonlight.data.mapper.toDomainModel
import ru.moonlight.data.paging.mediator.ProductMediator
import ru.moonlight.database.MoonlightDatabase
import ru.moonlight.database.catalog.ProductEntity
import ru.moonlight.domain_model.catalog.CatalogProductDomainModel
import ru.moonlight.domain_model.catalog.ProductDetailsDomainModel
import ru.moonlight.domain_model.catalog.ProductMetadataDomainModel
import ru.moonlight.network.service.CatalogService
import javax.inject.Inject

interface CatalogRepository {
    suspend fun getProductMetadata(category: String): ApiResponse<ProductMetadataDomainModel>
    fun getItemsByCategoryPaging(
        category: String,
        sortBy: String? = null,
        minPrice: Float? = null,
        maxPrice: Float? = null,
        sizes: List<Float>? = null,
        audiences: Int? = null,
        materials: Int? = null,
        treasures: Int? = null,
    ): Flow<PagingData<CatalogProductDomainModel>>
    suspend fun getCountOfProducts(
        category: String,
        sortBy: String? = null,
        minPrice: Float? = null,
        maxPrice: Float? = null,
        sizes: List<Float>? = null,
        audiences: Int? = null,
        materials: Int? = null,
        treasures: Int? = null,
    ): ApiResponse<Int>
    suspend fun getProductDetailsById(id: Long): ApiResponse<ProductDetailsDomainModel>
    suspend fun clearDatabase()
}

internal class CatalogRepositoryImpl @Inject constructor(
    private val service: CatalogService,
    private val database: MoonlightDatabase,
    @Dispatcher(MoonlightDispatchers.IO) private val dispatcherIO: CoroutineDispatcher,
): CatalogRepository {
    override suspend fun getProductMetadata(category: String): ApiResponse<ProductMetadataDomainModel> = withContext(dispatcherIO) {
        return@withContext when (val response = service.getProductMetadata(category = category)) {
            is ApiResponse.Error -> ApiResponse.Error(response.msg)
            is ApiResponse.Success -> ApiResponse.Success(response.data?.toDomainModel())
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getItemsByCategoryPaging(
        category: String,
        sortBy: String?,
        minPrice: Float?,
        maxPrice: Float?,
        sizes: List<Float>?,
        audiences: Int?,
        materials: Int?,
        treasures: Int?,
    ): Flow<PagingData<CatalogProductDomainModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = ProductMediator(
                service = service,
                database = database,
                category = category,
                sortBy = sortBy,
                minPrice = minPrice,
                maxPrice = maxPrice,
                sizes = sizes,
                audiences = audiences,
                materials = materials,
                treasures = treasures,
            ),
            pagingSourceFactory = {
                database.productDao.productPagingSource()
            }
        ).flow
            .flowOn(dispatcherIO)
            .map { data: PagingData<ProductEntity> ->
                data.map { product -> product.mapToDomain() }
            }
    }

    override suspend fun getCountOfProducts(
        category: String,
        sortBy: String?,
        minPrice: Float?,
        maxPrice: Float?,
        sizes: List<Float>?,
        audiences: Int?,
        materials: Int?,
        treasures: Int?,
    ): ApiResponse<Int> = withContext(dispatcherIO) {
        val response = service.getProductItems(
            page = 1,
            category = category,
            sortBy = sortBy,
            minPrice = minPrice,
            maxPrice = maxPrice,
            sizes = sizes,
            audiences = audiences,
            materials = materials,
            treasures = treasures,
        )

        return@withContext when (response) {
            is ApiResponse.Error -> ApiResponse.Error(response.msg)
            is ApiResponse.Success -> {
                ApiResponse.Success(response.data?.productPage?.totalElements)
            }
        }
    }

    override suspend fun getProductDetailsById(id: Long): ApiResponse<ProductDetailsDomainModel> = withContext(dispatcherIO) {
        return@withContext when (val response = service.getProductDetails(id = id)) {
            is ApiResponse.Error -> ApiResponse.Error(msg = response.msg)
            is ApiResponse.Success -> ApiResponse.Success(data = response.data?.mapToDomain())
        }
    }

    override suspend fun clearDatabase() = withContext(dispatcherIO) {
        database.productDao.clearAll()
    }

}