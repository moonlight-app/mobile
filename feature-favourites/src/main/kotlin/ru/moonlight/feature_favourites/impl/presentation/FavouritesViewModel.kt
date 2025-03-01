package ru.moonlight.feature_favourites.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.moonlight.api.component.FavoriteProductFeedModel
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.domain.favorite.interactor.ChangeFavoriteProductStatus
import ru.moonlight.domain.favorite.interactor.GetFavoritesInteractor
import ru.moonlight.domain_model.catalog.CatalogProductDomainModel
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val getFavoritesInteractor: GetFavoritesInteractor,
    private val changeFavoriteProductStatus: ChangeFavoriteProductStatus,
): ViewModel() {

    private var _uiState = MutableStateFlow<BaseUIState>(BaseUIState.Loading)
    val uiState = _uiState.asStateFlow()

    private var _orders = MutableStateFlow<PagingData<FavoriteProductFeedModel>>(
        PagingData.empty(
        sourceLoadStates = LoadStates(
            refresh = LoadState.Loading,
            prepend = LoadState.Loading,
            append = LoadState.Loading,
        )
    ))
    val orders = _orders.asStateFlow()

    fun dispatch(action: FavouritesAction) {
        when (action) {
            is FavouritesAction.LoadData -> loadFavourites()
            is FavouritesAction.ChangeProductFavoriteStatus -> changeProductStatus(action.id, action.status)
        }
    }

    private fun loadFavourites() {
        viewModelScope.launch {
            _uiState.value = BaseUIState.Loading

            getFavoritesInteractor()
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _orders.emit(pagingData.map { it.mapToUiModel() })
                    _uiState.value = BaseUIState.Success
                }
        }
    }

    private fun changeProductStatus(id: Long, status: Boolean) {
        viewModelScope.launch {
            changeFavoriteProductStatus(id = id, isFavorite = status)
        }
    }
}

private fun CatalogProductDomainModel.mapToUiModel() =
    FavoriteProductFeedModel(
        id = this.id,
        title = this.title,
        price = this.price,
        previewUrl = this.previewUrl,
        isFavorite = this.isFavorite,
    )