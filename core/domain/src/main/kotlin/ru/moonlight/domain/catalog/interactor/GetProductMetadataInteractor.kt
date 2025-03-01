package ru.moonlight.domain.catalog.interactor

import ru.moonlight.data.repository.CatalogRepository
import javax.inject.Inject

class GetProductMetadataInteractor @Inject constructor(
    private val repository: CatalogRepository,
) {
    suspend operator fun invoke(productType: String) = repository.getProductMetadata(productType)
}