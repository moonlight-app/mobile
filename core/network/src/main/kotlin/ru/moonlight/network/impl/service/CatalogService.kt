package ru.moonlight.network.impl.service

import ru.moonlight.common.ApiResponse
import ru.moonlight.network.api.model.catalog.CatalogResponse
import ru.moonlight.network.api.model.catalog.ProductDetailsResponse
import ru.moonlight.network.api.model.catalog.ProductTypeMetadataResponse
import ru.moonlight.network.api.service.CatalogService
import ru.moonlight.network.impl.api.CatalogApi
import ru.moonlight.network.impl.utils.ApiCallController
import javax.inject.Inject

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
