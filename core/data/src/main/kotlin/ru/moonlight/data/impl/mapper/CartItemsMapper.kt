package ru.moonlight.data.impl.mapper

import ru.moonlight.database.impl.cart.CartForMediatorEntity
import ru.moonlight.database.impl.cart.LocalCartEntity
import ru.moonlight.domain_model.cart.CartItemDomainModel
import ru.moonlight.network.api.model.cart.CartItem

internal fun CartItem.mapToDomain() =
    CartItemDomainModel(
        count = this.count,
        createdAt = this.createdAt,
        isFavorite = this.isFavorite,
        itemId = this.itemId,
        name = this.name,
        previewUrl = this.previewUrl,
        price = this.price,
        productId = this.productId,
        size = this.size,
        type = this.type,
        isChosen = false,
    )

internal fun CartForMediatorEntity.mapToDomain() =
    CartItemDomainModel(
        count = this.count,
        createdAt = this.createdAt,
        isFavorite = this.isFavorite,
        itemId = this.itemId,
        name = this.name,
        previewUrl = this.previewUrl,
        price = this.price,
        productId = this.productId,
        size = this.size,
        type = this.type,
        isChosen = false,
    )

internal fun CartItem.mapToMediatorCartEntity() =
    CartForMediatorEntity(
        itemId = this.itemId,
        productId = this.productId,
        count = this.count,
        name = this.name,
        previewUrl = this.previewUrl,
        price = this.price,
        size = this.size,
        type = this.type,
        createdAt = this.createdAt,
        isFavorite = this.isFavorite,
    )

internal fun CartItemDomainModel.mapToEntity() =
    CartForMediatorEntity(
        itemId = this.itemId,
        productId = this.productId,
        count = this.count,
        name = this.name,
        previewUrl = this.previewUrl,
        price = this.price,
        size = this.size,
        type = this.type,
        createdAt = this.createdAt,
        isFavorite = this.isFavorite,
    )

internal fun CartItem.mapToLocalCartEntity() =
    LocalCartEntity(
        itemId = this.itemId,
        productId = this.productId,
        count = this.count,
        createdAt = this.createdAt,
        isFavorite = this.isFavorite,
        chosen = true,
    )