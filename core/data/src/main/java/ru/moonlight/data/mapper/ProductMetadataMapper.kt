package ru.moonlight.data.mapper

import ru.moonlight.domain_model.catalog.ProductMetadataDomainModel
import ru.moonlight.network.model.catalog.ProductTypeMetadataResponse

fun ProductTypeMetadataResponse.toDomainModel() =
    ProductMetadataDomainModel(
        popularSizes = this.popularSizes,
        minPrice = this.priceRange.min,
        maxPrice = this.priceRange.max
    )