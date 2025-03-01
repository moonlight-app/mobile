package ru.moonlight.feature_order.impl.presentation

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
import ru.moonlight.api.component.OrderUiModel
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.domain.order.interactor.GetOrdersInteractor
import ru.moonlight.domain_model.order.OrdersDomainModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getOrdersInteractor: GetOrdersInteractor,
): ViewModel() {

    private var _uiState = MutableStateFlow<BaseUIState>(BaseUIState.Loading)
    val uiState = _uiState.asStateFlow()

    private var _orders = MutableStateFlow<PagingData<OrderUiModel>>(
        PagingData.empty(
        sourceLoadStates = LoadStates(
            refresh = LoadState.Loading,
            prepend = LoadState.Loading,
            append = LoadState.Loading,
        )
    ))
    val orders = _orders.asStateFlow()

    fun dispatch(action: OrderAction) {
        when (action) {
            is OrderAction.LoadData -> loadFavourites()
        }
    }

    private fun loadFavourites() {
        viewModelScope.launch {
            _uiState.value = BaseUIState.Loading

            getOrdersInteractor()
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _orders.value = pagingData.map { it.mapToUiModel() }
                    _uiState.value = BaseUIState.Success
                }
        }
    }

}

private fun OrdersDomainModel.mapToUiModel() =
    OrderUiModel(
        id = productId,
        title = name,
        type = type,
        imageUrl = previewUrl,
        price = price.toString(),
        size = size,
        status = status,
    )