package ru.moonlight.network.model.catalog

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductTypeMetadataResponse(
    @SerialName("popular_sizes") val popularSizes: List<Double> = emptyList(),
    @SerialName("price_range") val priceRange: PriceRange,
    @SerialName("product_type") val productType: String,
)

@Serializable
data class PriceRange(
    val max: Double,
    val min: Double,
)