package ru.moonlight.data.impl.mapper

import ru.moonlight.domain_model.catalog.ProductDetailsDomainModel
import ru.moonlight.network.api.model.catalog.ProductDetailsResponse

internal fun ProductDetailsResponse.mapToDomain() =
    ProductDetailsDomainModel(
        imageUrl = this.previewUrl,
        id = this.id,
        article = this.article,
        title = this.name,
        price = this.price.toString(),
        sizes = this.sizes,
        material = this.material.toString(),
        standard = this.sample,
        typeOfStandard = this.sampleType,
        insertion = this.treasures.toString(),
        weight = this.weight.toString(),
        description = this.description,
        isFavourite = this.isFavourite,
    )
