package ru.moonlight.feature_catalog.impl.presentation

import ru.moonlight.feature_catalog_filters.api.CatalogFilter
import ru.moonlight.feature_catalog_sort.api.CatalogSortType

internal sealed class CatalogAction {
    data class LoadCatalog(val category: String) : CatalogAction()
    data class UpdateCatalogFilter(val filter: CatalogFilter) : CatalogAction()
    data class UpdateCatalogSortType(val sortType: CatalogSortType) : CatalogAction()
    data class ProductClick(val id: Long) : CatalogAction()
    data class FavouriteClick(val id: Long, val status: Boolean) : CatalogAction()
    data object BackClick : CatalogAction()
}