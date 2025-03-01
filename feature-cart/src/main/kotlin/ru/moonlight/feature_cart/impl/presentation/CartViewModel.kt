package ru.moonlight.feature_cart.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.moonlight.api.component.CartFeedModel
import ru.moonlight.common.ApiResponse
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.data.repository.CartRepository
import ru.moonlight.domain.cart.interactor.ChangeChoseStatusInteractor
import ru.moonlight.domain.cart.interactor.DecreaseCountOfProductsInteractor
import ru.moonlight.domain.cart.interactor.DeleteProductFromCartInteractor
import ru.moonlight.domain.cart.interactor.IncreaseCountOfProductsInteractor
import ru.moonlight.domain.favorite.interactor.ChangeFavoriteProductStatus
import ru.moonlight.domain.order.usecase.CreateOrderUseCase
import ru.moonlight.domain_model.cart.CartItemDomainModel
import javax.inject.Inject

@HiltViewModel
internal class CartViewModel @Inject constructor(
    private val changeChoseStatusInteractor: ChangeChoseStatusInteractor,
    private val deleteProductFromCartInteractor: DeleteProductFromCartInteractor,
    private val increaseCountOfProductsInteractor: IncreaseCountOfProductsInteractor,
    private val decreaseCountOfProductsInteractor: DecreaseCountOfProductsInteractor,
    private val changeFavoriteProductStatus: ChangeFavoriteProductStatus,
    private val createOrderInteractor: CreateOrderUseCase,
    private val repository: CartRepository,
): ViewModel() {

    private var _uiState = MutableStateFlow<BaseUIState>(BaseUIState.Loading)
    val uiState = _uiState.asStateFlow()

    private var _cartUIState = MutableStateFlow<CartUIState>(CartUIState.ChooseItems())
    val cartUIState = _cartUIState.asStateFlow()

    private var _cartItems = MutableStateFlow<PagingData<CartFeedModel>>(PagingData.empty(sourceLoadStates = LoadStates(
        refresh = LoadState.Loading,
        prepend = LoadState.Loading,
        append = LoadState.Loading,
    )))
    val cartItems = _cartItems.asStateFlow()

    fun dispatch(action: CartAction) {
        viewModelScope.launch {
            when (action) {
                is CartAction.LoadCart -> loadCart.collect { pagingData ->
                    _cartItems.emit(pagingData.map { product -> product.mapToFeedModel() })
                    _uiState.value = BaseUIState.Success
                }
                is CartAction.ChangeChoseStatus -> changeChosenStatus(action.itemId, action.status)
                is CartAction.ChangeChoseStatusForAllItems -> changeChosenStatusForAllItems(action.status)
                is CartAction.DeleteItemFromCart -> deleteProductFromCart(action.itemId)
                is CartAction.IncreaseProductCount -> increaseProductCount(itemId = action.itemId, currentCount = action.currentCount)
                is CartAction.DecreaseProductCount -> decreaseProductCount(itemId = action.itemId, currentCount = action.currentCount)
                is CartAction.CompleteOrder -> createOrder()
                is CartAction.ClearUiState -> _cartUIState.value = CartUIState.ChooseItems()
                is CartAction.ChangeProductFavoriteStatus -> setProductFavoriteStatus(action.id, action.status)
            }
        }
    }

    private val loadCart: Flow<PagingData<CartItemDomainModel>> =
        createPagingSourceFlow()
            .cachedIn(viewModelScope)


    private fun createPagingSourceFlow(): Flow<PagingData<CartItemDomainModel>> {
        val newSource = repository.getProductsInCartPagingData(scope = viewModelScope)
        return newSource
    }

    private fun deleteProductFromCart(productId: Long) {
        viewModelScope.launch {
            deleteProductFromCartInteractor(productId)
        }
    }

    private fun changeChosenStatus(productId: Long, status: Boolean) {
        viewModelScope.launch {
            changeChoseStatusInteractor.changeStatusForItem(productId, status)
        }
    }

    private fun changeChosenStatusForAllItems(status: Boolean) {
        viewModelScope.launch {
            changeChoseStatusInteractor.changeStatusForAllItems(status = status)
        }
    }

    private fun setProductFavoriteStatus(id: Long, status: Boolean) {
        viewModelScope.launch {
            changeFavoriteProductStatus(id, status)
        }
    }

    private fun increaseProductCount(itemId: Long, currentCount: Int) {
        viewModelScope.launch {
            increaseCountOfProductsInteractor(itemId = itemId, currentCount = currentCount)
        }
    }

    private fun decreaseProductCount(itemId: Long, currentCount: Int) {
        viewModelScope.launch {
            decreaseCountOfProductsInteractor(itemId = itemId, currentCount = currentCount)
        }
    }

    private fun createOrder() {
        viewModelScope.launch {
            when (val response = createOrderInteractor()) {
                is ApiResponse.Error -> _uiState.value = BaseUIState.Error(response.msg)
                is ApiResponse.Success -> _cartUIState.value = CartUIState.OrderCompleted()
            }
        }
    }

}

private fun CartItemDomainModel.mapToFeedModel() =
    CartFeedModel(
        itemId = this.itemId,
        productId = this.productId,
        imageUrl = this.previewUrl,
        price = this.price.toString(),
        size = this.size,
        count = this.count,
        isFavourite = this.isFavorite,
        chosen = this.isChosen,
    )