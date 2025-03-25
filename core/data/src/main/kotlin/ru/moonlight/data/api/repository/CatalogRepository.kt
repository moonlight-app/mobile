package ru.moonlight.data.api.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.moonlight.common.ApiResponse
import ru.moonlight.domain_model.catalog.CatalogProductDomainModel
import ru.moonlight.domain_model.catalog.ProductDetailsDomainModel
import ru.moonlight.domain_model.catalog.ProductMetadataDomainModel

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