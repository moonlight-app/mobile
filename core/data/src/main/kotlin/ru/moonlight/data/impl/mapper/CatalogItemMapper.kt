package ru.moonlight.data.impl.mapper

import ru.moonlight.database.impl.catalog.ProductEntity
import ru.moonlight.domain_model.catalog.CatalogProductDomainModel
import ru.moonlight.network.api.model.catalog.Product

fun ProductEntity.mapToDomain() =
    CatalogProductDomainModel(
        id = this.productId,
        previewUrl = this.previewUrl,
        title = this.name,
        price = this.price,
        isFavorite = this.isFavorite,
    )

fun Product.mapToDomain() =
    CatalogProductDomainModel(
        id = this.productId,
        previewUrl = this.previewUrl,
        title = this.name,
        price = this.price,
        isFavorite = this.isFavorite,
    )

fun Product.mapToEntity() =
    ProductEntity(
        productId = this.productId,
        previewUrl = this.previewUrl,
        name = this.name,
        price = this.price,
        isFavorite = this.isFavorite,
    )