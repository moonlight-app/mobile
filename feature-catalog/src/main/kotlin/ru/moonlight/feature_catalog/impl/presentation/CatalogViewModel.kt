package ru.moonlight.feature_catalog.impl.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import ru.moonlight.common.ApiResponse
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.common.base.BaseViewModel
import ru.moonlight.domain.catalog.GetCatalogItemsPagingInterceptor
import ru.moonlight.domain.catalog.GetProductMetadata
import ru.moonlight.feature_catalog_filters.api.CatalogFilter
import ru.moonlight.feature_catalog_sort.api.CatalogSortType
import javax.inject.Inject

@HiltViewModel
internal class CatalogViewModel @Inject constructor(
    private val getProductMetadata: GetProductMetadata,
    private val getCatalogItemsPagingInterceptor: GetCatalogItemsPagingInterceptor,
): BaseViewModel<CatalogState, CatalogSideEffect>(CatalogState()) {
    val uiState = baseUiState

    fun getCategoryMetadata(category: String) = intent {
        updateUiState(BaseUIState.Loading)

        val metadata = getProductMetadata(category)
        when (metadata) {
            is ApiResponse.Error -> {
                updateUiState(BaseUIState.Error(metadata.msg))
            }
            is ApiResponse.Success -> {
                reduce {
                    state.copy(
                        catalogFilter = CatalogFilter(
                            defaultSize = metadata.data!!.popularSizes,
                            defaultMinPrice = metadata.data?.minPrice.toString(),
                            defaultMaxPrice = metadata.data?.maxPrice.toString(),
                        )
                    )
                }
                updateUiState(BaseUIState.Success)
            }
        }
    }

    fun getProducts(category: String) = getCatalogItemsPagingInterceptor(category)
        .map { pagingData -> pagingData.map { product -> product.mapToPresentation() } }
        .cachedIn(viewModelScope)

    fun setCatalogFilters(filters: CatalogFilter) = intent {
        reduce { state.copy(catalogFilter = filters) }
    }

    fun setCatalogSortType(sortType: CatalogSortType) = intent {
        reduce { state.copy(catalogSort = sortType) }
    }

}