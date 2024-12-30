package ru.moonlight.domain_model.catalog

data class CatalogProductDomainModel(
    val id: Int,
    val previewUrl: String,
    val title: String,
    val price: Double,
    val isFavorite: Boolean,
)