package ru.moonlight.feature_catalog.impl.presentation

import ru.moonlight.api.component.ProductFeedModel
import ru.moonlight.domain_model.catalog.CatalogProductDomainModel
import ru.moonlight.feature_catalog_filters.api.CatalogFilter
import ru.moonlight.feature_catalog_sort.api.CatalogSortType

internal data class CatalogState(
    val catalogFilter: CatalogFilter? = null,
    val catalogSort: CatalogSortType = CatalogSortType.POPULARITY,
)

internal fun CatalogProductDomainModel.mapToPresentation() =
    ProductFeedModel(
        id = this.id,
        title = this.title,
        price = this.price,
        previewUrl = this.previewUrl,
        isFavorite = this.isFavorite,
    )






