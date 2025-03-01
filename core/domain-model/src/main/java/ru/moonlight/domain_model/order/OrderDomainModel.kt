package ru.moonlight.domain_model.order

data class OrdersDomainModel(
    val orderId: Long,
    val productId: Long,
    val count: Int,
    val createdAt: String,
    val name: String,
    val previewUrl: String,
    val price: Double,
    val size: String?,
    val status: String,
    val type: String
)