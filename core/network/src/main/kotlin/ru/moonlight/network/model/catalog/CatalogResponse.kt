package ru.moonlight.network.model.catalog

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatalogResponse(
    @SerialName("content") val products: List<Product>,
    @SerialName("page") val productPage: ProductPage,
)

@Serializable
data class Product(
    @SerialName("id") val productId: Long,
    val name: String,
    @SerialName("preview_url") val previewUrl: String,
    val price: Double,
    @SerialName("is_favorite") val isFavorite: Boolean = false,
)

@Serializable
data class ProductPage(
    val number: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
)