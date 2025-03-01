package ru.moonlight.network.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.moonlight.network.model.catalog.CatalogResponse
import ru.moonlight.network.model.catalog.ProductDetailsResponse
import ru.moonlight.network.model.catalog.ProductTypeMetadataResponse

internal interface CatalogApi {

    @GET("/api/catalog/metadata/{product_type}")
    suspend fun catalogMetadata(
        @Path("product_type") productType: String,
    ): Response<ProductTypeMetadataResponse>

    @GET("/api/catalog/items/{product_type}")
    suspend fun catalogItems(
        @Path("product_type") productType: String,
        @Query("sort_by") sortBy: String? = null,
        @Query("min_price") minPrice: Float? = null,
        @Query("max_price") maxPrice: Float? = null,
        @Query("sizes") sizes: List<Float>? = null,
        @Query("audiences") audiences: Int? = null,
        @Query("materials") materials: Int? = null,
        @Query("treasures") treasures: Int? = null,
        @Query("page") page: Int,
    ): Response<CatalogResponse>

    @GET("/api/catalog/item/{product_id}")
    suspend fun catalogItemDetails(@Path("product_id") id: String): Response<ProductDetailsResponse>

}