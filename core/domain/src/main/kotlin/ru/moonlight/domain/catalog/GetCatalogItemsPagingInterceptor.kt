package ru.moonlight.domain.catalog

import ru.moonlight.data.repository.CatalogRepository
import javax.inject.Inject

class GetCatalogItemsPagingInterceptor @Inject constructor(
    private val repository: CatalogRepository,
) {
    operator fun invoke(productType: String) = repository.getItemsByCategoryPaging(productType)
}