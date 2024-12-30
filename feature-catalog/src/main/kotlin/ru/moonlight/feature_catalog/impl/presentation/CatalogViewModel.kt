package ru.moonlight.feature_catalog.impl.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun getMetadata(category: String) = intent {
        updateUiState(BaseUIState.Loading)

        val metadata = getProductMetadata(category)
        when (metadata) {
            is ApiResponse.Error -> {
                Log.e("TAG", "getProductMetaData: ERRORRRR ${metadata.msg}", )
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
                Log.e("TAG", "getProductMetaData: POPALLL", )
                updateUiState(BaseUIState.Success)
            }
        }
    }

    fun getPagingData(category: String) = getCatalogItemsPagingInterceptor(category)
        .cachedIn(viewModelScope)

    fun setFilters(filters: CatalogFilter) = intent {
        reduce { state.copy(catalogFilter = filters) }
    }

    fun setSortType(sortType: CatalogSortType) = intent {
        reduce { state.copy(catalogSort = sortType) }
    }

}