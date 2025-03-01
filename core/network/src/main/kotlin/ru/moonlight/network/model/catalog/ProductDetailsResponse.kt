package ru.moonlight.network.model.catalog

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailsResponse(
    @SerialName("preview_url") val previewUrl: String,
    val name: String,
    val price: Double,
    val sizes: List<String>? = null,
    @SerialName("materials") val material: Int? = null,
    val sample: String? = null,
    @SerialName("sample_type") val sampleType: String? = null,
    val treasures: Int? = null,
    val weight: Double? = null,
    val article: Int,
    val description: String,
    val id: Long,
    val type: String,
    @SerialName("is_favorite") val isFavourite: Boolean = false,
)