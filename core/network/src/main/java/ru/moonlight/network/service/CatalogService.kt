package ru.moonlight.network.service

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.moonlight.common.ApiResponse
import ru.moonlight.network.api.CatalogApi
import ru.moonlight.network.datasource.CatalogPagingSource
import ru.moonlight.network.model.catalog.Product
import ru.moonlight.network.model.catalog.ProductTypeMetadataResponse
import ru.moonlight.network.utils.ApiCallController
import javax.inject.Inject

interface CatalogService {
    suspend fun getProductMetadata(category: String): ApiResponse<ProductTypeMetadataResponse>
    fun getItemsByCategoryPaging(category: String): Flow<PagingData<Product>>
}

internal class CatalogApiService @Inject constructor(
    private val api: CatalogApi,
    private val apiCallController: ApiCallController,
): CatalogService {
    override suspend fun getProductMetadata(category: String): ApiResponse<ProductTypeMetadataResponse> =
        apiCallController.safeApiCall { api.catalogMetadata(productType = category) }

    override fun getItemsByCategoryPaging(category: String): Flow<PagingData<Product>> =
        Pager(
            config = PagingConfig(pageSize = 20, maxSize = 200),
            pagingSourceFactory = { CatalogPagingSource(api, apiCallController, category) }
        ).flow

}
