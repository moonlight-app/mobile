package ru.moonlight.domain.catalog.interactor

import ru.moonlight.common.ApiResponse
import ru.moonlight.data.api.repository.CatalogRepository
import ru.moonlight.domain.catalog.model.MaterialDomain
import ru.moonlight.domain.catalog.model.TreasureDomain
import ru.moonlight.domain.util.toEnum
import ru.moonlight.domain_model.catalog.ProductDetailsDomainModel
import javax.inject.Inject

class GetProductDetailsInteractor @Inject constructor(
    private val repository: CatalogRepository,
) {
    suspend operator fun invoke(productId: Long): ApiResponse<ProductDetailsDomainModel> {
        return when (val response = repository.getProductDetailsById(productId)) {
            is ApiResponse.Error -> ApiResponse.Error(response.msg)
            is ApiResponse.Success -> ApiResponse.Success(response.data?.convertBackendConstants())
        }
    }

}

private fun ProductDetailsDomainModel.convertBackendConstants() =
    this.copy(
        material = material?.toIntOrNull()?.toEnum<MaterialDomain>()?.name ?: "Unknown material",
        insertion = insertion?.toIntOrNull()?.toEnum<TreasureDomain>()?.name ?: "Unknown treasure",
        weight = if (weight == "null") null else weight
    )