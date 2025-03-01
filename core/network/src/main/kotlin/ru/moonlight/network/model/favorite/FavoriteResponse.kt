package ru.moonlight.network.model.favorite

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteResponse(
    @SerialName("content") val favoriteProducts: List<FavoriteProducts>,
    @SerialName("page") val productPage: FavoriteProductPage,
)

@Serializable
data class FavoriteProducts(
    @SerialName("id") val productId: Long,
    val name: String,
    @SerialName("preview_url") val previewUrl: String,
    val price: Double,
    @SerialName("is_favorite") val isFavorite: Boolean = true,
    val type: String,
    @SerialName("created_at") val createdAt: String,
)

@Serializable
data class FavoriteProductPage(
    val number: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
)