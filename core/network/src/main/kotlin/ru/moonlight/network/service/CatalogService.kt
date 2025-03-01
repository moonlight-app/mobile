package ru.moonlight.network.service

import ru.moonlight.common.ApiResponse
import ru.moonlight.network.api.CatalogApi
import ru.moonlight.network.model.catalog.CatalogResponse
import ru.moonlight.network.model.catalog.ProductDetailsResponse
import ru.moonlight.network.model.catalog.ProductTypeMetadataResponse
import ru.moonlight.network.utils.ApiCallController
import javax.inject.Inject

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

internal class CatalogApiService @Inject constructor(
    private val api: CatalogApi,
    private val apiCallController: ApiCallController,
): CatalogService {
    override suspend fun getProductMetadata(category: String): ApiResponse<ProductTypeMetadataResponse> =
        apiCallController.safeApiCall { api.catalogMetadata(productType = category) }

    override suspend fun getProductItems(
        category: String,
        sortBy: String?,
        minPrice: Float?,
        maxPrice: Float?,
        sizes: List<Float>?,
        audiences: Int?,
        materials: Int?,
        treasures: Int?,
        page: Int,
    ): ApiResponse<CatalogResponse> =
        apiCallController.safeApiCall {
            api.catalogItems(
                productType = category,
                sortBy = sortBy,
                minPrice = minPrice,
                maxPrice = maxPrice,
                sizes = sizes,
                audiences = audiences,
                materials = materials,
                treasures = treasures,
                page = page,
            )
        }

    override suspend fun getProductDetails(id: Long): ApiResponse<ProductDetailsResponse> =
        apiCallController.safeApiCall {
            api.catalogItemDetails(id = id.toString())
        }

}
