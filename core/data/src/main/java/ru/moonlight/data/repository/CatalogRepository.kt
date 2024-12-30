package ru.moonlight.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.moonlight.common.ApiResponse
import ru.moonlight.common.di.Dispatcher
import ru.moonlight.common.di.MoonlightDispatchers
import ru.moonlight.data.mapper.mapToDomain
import ru.moonlight.data.mapper.toDomainModel
import ru.moonlight.domain_model.catalog.CatalogProductDomainModel
import ru.moonlight.domain_model.catalog.ProductMetadataDomainModel
import ru.moonlight.network.model.catalog.Product
import ru.moonlight.network.service.CatalogService
import javax.inject.Inject

interface CatalogRepository {
    suspend fun getProductMetadata(category: String): ApiResponse<ProductMetadataDomainModel>
    fun getItemsByCategoryPaging(category: String): Flow<PagingData<CatalogProductDomainModel>>
}

internal class CatalogRepositoryImpl @Inject constructor(
    private val service: CatalogService,
    @Dispatcher(MoonlightDispatchers.IO) private val dispatcherIO: CoroutineDispatcher,
): CatalogRepository {
    override suspend fun getProductMetadata(category: String): ApiResponse<ProductMetadataDomainModel> {
        return when (val response = service.getProductMetadata(category = category)) {
            is ApiResponse.Error -> ApiResponse.Error(response.msg)
            is ApiResponse.Success -> ApiResponse.Success(response.data?.toDomainModel())
        }
    }

    override fun getItemsByCategoryPaging(category: String): Flow<PagingData<CatalogProductDomainModel>> =
        service.getItemsByCategoryPaging(category)
            .flowOn(dispatcherIO)
            .map { data: PagingData<Product> ->
                data.map { product -> product.mapToDomain() }
            }

}