package ru.moonlight.feature_catalog.impl.domain

import ru.moonlight.domain.catalog.model.AudienceDomain
import ru.moonlight.domain.catalog.model.CatalogParametersDomainModel
import ru.moonlight.domain.catalog.model.MaterialDomain
import ru.moonlight.domain.catalog.model.SortByDomain
import ru.moonlight.domain.catalog.model.TreasureDomain
import ru.moonlight.feature_catalog_filters.api.Audience
import ru.moonlight.feature_catalog_filters.api.Material
import ru.moonlight.feature_catalog_filters.api.TreasureInsert
import ru.moonlight.feature_catalog_sort.api.CatalogSortType

internal data class CatalogParameters(
    val productType: String,
    val sortBy: CatalogSortType,
    val minPrice: String?,
    val maxPrice: String?,
    val sizes: List<Float>?,
    val audiences: List<Audience>?,
    val materials: List<Material>?,
    val treasures: List<TreasureInsert>?,
)

internal fun CatalogParameters.mapToDomain() = CatalogParametersDomainModel(
    productType = productType,
    sortBy = sortBy.mapToDomain(),
    minPrice = minPrice?.toFloat(),
    maxPrice = maxPrice?.toFloat(),
    sizes = sizes,
    audiences = audiences?.map { it.mapToDomain()},
    materials = materials?.map { it.mapToDomain() },
    treasures = treasures?.map { it.mapToDomain() },
)

internal fun CatalogSortType.mapToDomain() = when (this) {
    CatalogSortType.POPULARITY -> SortByDomain.POPULARITY
    CatalogSortType.INCREASE_PRICE -> SortByDomain.PRICE_ASC
    CatalogSortType.DECREASE_PRICE -> SortByDomain.PRICE_DESC
}

internal fun Audience.mapToDomain() = when (this) {
    Audience.MAN -> AudienceDomain.MAN
    Audience.WOMAN -> AudienceDomain.WOMAN
    Audience.CHILDREN -> AudienceDomain.CHILDREN
    Audience.UNISEX -> AudienceDomain.UNISEX
}

internal fun Material.mapToDomain() = when (this) {
    Material.GOLD -> MaterialDomain.GOLD
    Material.SILVER -> MaterialDomain.SILVER
    Material.PLATINUM -> MaterialDomain.PLATINUM
    Material.WHITE_GOLD -> MaterialDomain.WHITE_GOLD
    Material.PINK_GOLD -> MaterialDomain.PINK_GOLD
    Material.CERAMIC -> MaterialDomain.CERAMIC
}

internal fun TreasureInsert.mapToDomain() = when (this) {
    TreasureInsert.Brilliant -> TreasureDomain.DIAMOND
    TreasureInsert.Sapphire -> TreasureDomain.SAPPHIRE
    TreasureInsert.PEARL -> TreasureDomain.PEARL
    TreasureInsert.AMETHYST -> TreasureDomain.AMETHYST
    TreasureInsert.CUBIC_ZIRCONIA -> TreasureDomain.FIANIT
    TreasureInsert.EMERALD -> TreasureDomain.EMERALD
    TreasureInsert.RUBY -> TreasureDomain.RUBY
    TreasureInsert.NOTHING -> TreasureDomain.NOTHING
}
