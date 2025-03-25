package ru.moonlight.data.impl.mapper

import ru.moonlight.database.impl.favorite.FavoriteEntity
import ru.moonlight.domain_model.catalog.CatalogProductDomainModel
import ru.moonlight.network.api.model.favorite.FavoriteProducts

internal fun FavoriteProducts.mapToDomain() =
    CatalogProductDomainModel(
        id = this.productId,
        previewUrl = this.previewUrl,
        title = this.name,
        price = this.price,
        isFavorite = this.isFavorite,
    )

internal fun FavoriteEntity.mapToDomain() =
    CatalogProductDomainModel(
        id = this.productId,
        previewUrl = this.previewUrl,
        title = this.name,
        price = this.price,
        isFavorite = this.isFavorite,
    )

internal fun FavoriteProducts.mapToEntity() =
    FavoriteEntity(
        productId = this.productId,
        previewUrl = this.previewUrl,
        name = this.name,
        price = this.price,
        isFavorite = this.isFavorite,
        createdAt = this.createdAt,
        type = this.type,
    )