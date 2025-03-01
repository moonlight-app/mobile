package ru.moonlight.feature_catalog.impl.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.moonlight.api.component.ProductFeedModel
import ru.moonlight.common.ApiResponse
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.common.base.BaseViewModel
import ru.moonlight.common.di.CoroutineScopeAnnotation
import ru.moonlight.common.di.MoonlightScope
import ru.moonlight.data.repository.CatalogRepository
import ru.moonlight.domain.catalog.interactor.GetCatalogItemsPagingInterceptor
import ru.moonlight.domain.catalog.interactor.GetCountOfProductsInteractor
import ru.moonlight.domain.catalog.interactor.GetProductMetadataInteractor
import ru.moonlight.domain.favorite.interactor.ChangeFavoriteProductStatus
import ru.moonlight.feature_catalog.impl.domain.CatalogParameters
import ru.moonlight.feature_catalog.impl.domain.mapToDomain
import ru.moonlight.feature_catalog_filters.api.CatalogFilter
import ru.moonlight.feature_catalog_sort.api.CatalogSortType
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
internal class CatalogViewModel @Inject constructor(
    private val getProductMetadataInteractor: GetProductMetadataInteractor,
    private val getCatalogItemsPagingInterceptor: GetCatalogItemsPagingInterceptor,
    private val getCountOfProductsInteractor: GetCountOfProductsInteractor,
    private val changeFavoriteProductStatus: ChangeFavoriteProductStatus,
    private val repository: CatalogRepository,
    @CoroutineScopeAnnotation(scope = MoonlightScope.ApplicationScope) private val applicationCoroutineScope: CoroutineScope,
) : BaseViewModel<CatalogState, CatalogSideEffect>(CatalogState()) {
    val uiState = baseUiState

    fun dispatch(action: CatalogAction) {
        when (action) {
            is CatalogAction.LoadCatalog -> {
                viewModelScope.launch {
                    getCategoryMetadata(action.category)
                }
            }
            is CatalogAction.UpdateCatalogFilter -> {
                viewModelScope.launch {
                    setCatalogFilters(action.filter)
                }
            }
            is CatalogAction.UpdateCatalogSortType -> {
                viewModelScope.launch {
                    setCatalogSortType(action.sortType)
                }
            }
            is CatalogAction.ProductClick -> intent { postSideEffect(CatalogSideEffect.NavigateToProduct(productId = action.id)) }
            is CatalogAction.FavouriteClick -> { changeFavoriteStatus(action.id, action.status) }
            CatalogAction.BackClick -> intent { postSideEffect(CatalogSideEffect.NavigateBack) }
        }
    }

    private var _productPagingData = MutableStateFlow(PagingData.empty<ProductFeedModel>())
    val productPagingData = _productPagingData
        .cachedIn(viewModelScope)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            PagingData.empty(
                sourceLoadStates = LoadStates(
                    refresh = LoadState.Loading,
                    prepend = LoadState.Loading,
                    append = LoadState.Loading,
                )
            ),
        )

    private var _totalCountOfProducts = MutableStateFlow<Int>(0)
    val totalCountOfProducts = _totalCountOfProducts.asStateFlow()

    private fun getCategoryMetadata(category: String) {
        viewModelScope.launch {
            updateUiState(BaseUIState.Loading)
            intent {
                val metadata = getProductMetadataInteractor(productType = category)
                when (metadata) {
                    is ApiResponse.Error -> {
                        updateUiState(BaseUIState.Error(metadata.msg))
                    }
                    is ApiResponse.Success -> {
                        val newFilter = CatalogFilter(
                            defaultSize = metadata.data!!.popularSizes,
                            defaultMinPrice = metadata.data?.minPrice.toString(),
                            defaultMaxPrice = metadata.data?.maxPrice.toString(),
                        )
                        reduce {
                            state.copy(
                                catalogProductType = category,
                                catalogFilter = newFilter,
                            )
                        }

                        initProductPagingDataFlow()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun initProductPagingDataFlow() {
        container.stateFlow
            .map { state ->
                CatalogParameters(
                    productType = state.catalogProductType,
                    sortBy = state.catalogSort,
                    minPrice = state.catalogFilter?.minPrice,
                    maxPrice = state.catalogFilter?.maxPrice,
                    sizes = state.catalogFilter?.size,
                    audiences = state.catalogFilter?.forWhom,
                    materials = state.catalogFilter?.material,
                    treasures = state.catalogFilter?.treasureInsert,
                )
            }
            .flatMapLatest { catalogParams ->
                getCountOfProducts(catalogParams)
                createProductPagingDataFlow(catalogParams)
            }
            .collect {
                _productPagingData.value = it
                updateUiState(BaseUIState.Success)
            }
    }

    private fun createProductPagingDataFlow(parameters: CatalogParameters): Flow<PagingData<ProductFeedModel>> {
        val newSource = getCatalogItemsPagingInterceptor(parameters.mapToDomain())

        return newSource.map { pagingData ->
            pagingData.map { product -> product.mapToPresentation() }
        }
    }

    private fun getCountOfProducts(parameters: CatalogParameters) {
        viewModelScope.launch {
            val count = getCountOfProductsInteractor(parameters.mapToDomain())
            _totalCountOfProducts.emit(count.data ?: 0)
        }

    }

    private fun changeFavoriteStatus(id: Long, status: Boolean) {
        viewModelScope.launch {
            changeFavoriteProductStatus(id = id, isFavorite = status)
        }
    }

    private fun setCatalogFilters(filters: CatalogFilter) = intent {
        viewModelScope.launch {
            reduce { state.copy(catalogFilter = filters) }
        }
    }

    private fun setCatalogSortType(sortType: CatalogSortType) = intent {
        viewModelScope.launch {
            reduce { state.copy(catalogSort = sortType) }
        }
    }

    override fun onCleared() {
        applicationCoroutineScope.launch {
            repository.clearDatabase()
        }
        super.onCleared()
    }

}
