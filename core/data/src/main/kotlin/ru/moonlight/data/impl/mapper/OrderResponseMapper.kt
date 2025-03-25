package ru.moonlight.data.impl.mapper

import ru.moonlight.domain_model.order.OrdersDomainModel
import ru.moonlight.network.api.model.order.OrderContent

internal fun OrderContent.mapToDomain() =
    OrdersDomainModel(
        orderId = this.orderId,
        productId = this.productId,
        count = this.count,
        price = this.price,
        status = this.status,
        name = this.name,
        previewUrl = this.previewUrl,
        createdAt = this.createdAt,
        size = this.size,
        type = this.type,
    )