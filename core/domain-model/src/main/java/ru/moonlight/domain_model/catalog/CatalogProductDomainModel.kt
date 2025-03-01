package ru.moonlight.domain_model.catalog

data class CatalogProductDomainModel(
    val id: Long,
    val previewUrl: String,
    val title: String,
    val price: Double,
    val isFavorite: Boolean,
)