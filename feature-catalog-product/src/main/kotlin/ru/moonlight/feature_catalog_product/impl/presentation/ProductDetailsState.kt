package ru.moonlight.feature_catalog_product.impl.presentation

data class ProductDetailsState(
    val id: Long? = null,
    val imageUrl: String = "",
    val title: String = "",
    val price: String = "",
    val sizes: List<String> = emptyList(),
    val material: String = "",
    val standard: String = "",
    val typeOfStandard: String = "",
    val insertion: String = "",
    val weight: String = "",
    val article: String = "",
    val description: String = "",
    val choosenSize: String? = null,
    val isFavourite: Boolean = false,
)