package ru.moonlight.network.api.model.cart

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartItemsResponse(
    @SerialName("content") val productsInCart: List<CartItem>,
    @SerialName("page") val cartPage: CartPage,
)

@Serializable
data class CartItem(
    val count: Int,
    @SerialName("created_at") val createdAt: String,
    @SerialName("is_favorite") val isFavorite: Boolean,
    @SerialName("item_id") val itemId: Long,
    val name: String,
    @SerialName("preview_url") val previewUrl: String,
    val price: Double,
    @SerialName("product_id") val productId: Long,
    val size: String? = null,
    val type: String,
)

@Serializable
data class CartPage(
    val number: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int,
)