package ru.moonlight.domain.catalog.interactor

import ru.moonlight.data.repository.CatalogRepository
import ru.moonlight.domain.catalog.model.CatalogParametersDomainModel
import ru.moonlight.domain.util.toBitMask
import javax.inject.Inject

class GetCountOfProductsInteractor @Inject constructor(
    private val repository: CatalogRepository,
) {
    suspend operator fun invoke(
        catalogParameters: CatalogParametersDomainModel,
    ) = with(catalogParameters) {
        repository.getCountOfProducts(
            catalogParameters.productType,
            sortBy?.name?.lowercase(),
            minPrice,
            maxPrice,
            sizes,
            audiences?.toBitMask(),
            materials?.toBitMask(),
            treasures?.toBitMask(),
        )
    }

}