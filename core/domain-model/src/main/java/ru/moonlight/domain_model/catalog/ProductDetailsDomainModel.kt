package ru.moonlight.domain_model.catalog

data class ProductDetailsDomainModel(
    val imageUrl: String,
    val article: Int,
    val id: Long,
    val title: String,
    val price: String,
    val sizes: List<String>?,
    val material: String?,
    val standard: String?,
    val typeOfStandard: String?,
    val insertion: String?,
    val weight: String?,
    val description: String,
    val isFavourite: Boolean,
)