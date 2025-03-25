package ru.moonlight.network.api.service

import ru.moonlight.common.ApiResponse
import ru.moonlight.network.api.model.catalog.CatalogResponse
import ru.moonlight.network.api.model.catalog.ProductDetailsResponse
import ru.moonlight.network.api.model.catalog.ProductTypeMetadataResponse

interface CatalogService {
    suspend fun getProductMetadata(category: String): ApiResponse<ProductTypeMetadataResponse>
    suspend fun getProductItems(
        category: String,
        sortBy: String?,
        minPrice: Float?,
        maxPrice: Float?,
        sizes: List<Float>?,
        audiences: Int?,
        materials: Int?,
        treasures: Int?,
        page: Int,
    ): ApiResponse<CatalogResponse>
    suspend fun getProductDetails(id: Long): ApiResponse<ProductDetailsResponse>
}