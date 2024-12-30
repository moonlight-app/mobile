package ru.moonlight.domain.catalog

import ru.moonlight.data.repository.CatalogRepository
import javax.inject.Inject

class GetProductMetadata @Inject constructor(
    private val repository: CatalogRepository,
) {
    suspend operator fun invoke(productType: String) = repository.getProductMetadata(productType)
}