package ru.moonlight.data.impl.mapper

import ru.moonlight.domain_model.catalog.ProductMetadataDomainModel
import ru.moonlight.network.api.model.catalog.ProductTypeMetadataResponse

fun ProductTypeMetadataResponse.toDomainModel() =
    ProductMetadataDomainModel(
        popularSizes = this.popularSizes,
        minPrice = this.priceRange.min,
        maxPrice = this.priceRange.max
    )