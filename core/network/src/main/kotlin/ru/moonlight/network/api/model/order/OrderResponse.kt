package ru.moonlight.network.api.model.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderResponse(
    @SerialName("content") val orders: List<OrderContent>,
    @SerialName("page") val orderPage: OrderPage
)

@Serializable
data class OrderContent(
    @SerialName("item_id") val orderId: Long,
    @SerialName("product_id") val productId: Long,
    val count: Int,
    @SerialName("created_at") val createdAt: String,
    val name: String,
    @SerialName("preview_url") val previewUrl: String,
    val price: Double,
    val size: String? = null,
    val status: String,
    val type: String
)

@Serializable
data class OrderPage(
    val number: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
)