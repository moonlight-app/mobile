package ru.moonlight.feature_catalog.impl.presentation

import ru.moonlight.domain_model.catalog.CatalogProductDomainModel
import ru.moonlight.feature_catalog_filters.api.CatalogFilter
import ru.moonlight.feature_catalog_sort.api.CatalogSortType

internal data class CatalogState(
    val catalogFilter: CatalogFilter? = null,
    val catalogSort: CatalogSortType = CatalogSortType.POPULARITY,
)

internal data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val imageUrl: String,
    val isFavorite: Boolean,
)

internal fun CatalogProductDomainModel.mapToPresentation() =
    Product(
        id = this.id,
        title = this.title,
        price = this.price,
        imageUrl = this.previewUrl,
        isFavorite = this.isFavorite,
    )