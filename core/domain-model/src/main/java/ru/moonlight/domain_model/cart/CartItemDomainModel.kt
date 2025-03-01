package ru.moonlight.domain_model.cart

data class CartItemDomainModel(
    val count: Int,
    val createdAt: String,
    val isFavorite: Boolean,
    val itemId: Long,
    val name: String,
    val previewUrl: String,
    val price: Double,
    val productId: Long,
    val size: String?,
    val type: String,
    val isChosen: Boolean
)