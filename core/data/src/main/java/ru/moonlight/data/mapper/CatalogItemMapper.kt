package ru.moonlight.data.mapper

import ru.moonlight.domain_model.catalog.CatalogProductDomainModel
import ru.moonlight.network.model.catalog.Product

fun Product.mapToDomain() =
    CatalogProductDomainModel(
        id = this.id,
        previewUrl = this.previewUrl,
        title = this.name,
        price = this.price,
        isFavorite = this.isFavorite,
    )