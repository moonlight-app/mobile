package ru.moonlight.feature_catalog_product.impl.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.moonlight.common.ApiResponse
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.common.base.BaseViewModel
import ru.moonlight.domain.cart.interactor.AddProductToCartInteractor
import ru.moonlight.domain.catalog.interactor.GetProductDetailsInteractor
import ru.moonlight.domain.favorite.interactor.ChangeFavoriteProductStatus
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductDetailsInteractor: GetProductDetailsInteractor,
    private val changeFavoriteProductStatus: ChangeFavoriteProductStatus,
    private val addProductToCartInteractor: AddProductToCartInteractor,
): BaseViewModel<ProductDetailsState, ProductDetailsSideEffect>(ProductDetailsState()) {

    val uiState = baseUiState

    fun dispatch(action: ProductDetailsAction) {
        when (action) {
            is ProductDetailsAction.LoadDetails -> loadProductDetails(action.productId)
            is ProductDetailsAction.UpdateChosenSize -> setChosenSize(action.size)
            is ProductDetailsAction.ChangeFavouritesStatus -> {
                changeFavouritesStatus()
            }
            is ProductDetailsAction.AddToCart -> addToCart()
            is ProductDetailsAction.GoToCartClick -> intent { postSideEffect(ProductDetailsSideEffect.NavigateToCart()) }
            is ProductDetailsAction.GoToAuthClick -> intent { postSideEffect(ProductDetailsSideEffect.NavigateToAuthorize()) }
        }
    }

    private fun loadProductDetails(id: Long) {
        updateUiState(BaseUIState.Loading)

        viewModelScope.launch {
            intent {
                when (val response = getProductDetailsInteractor(id)) {
                    is ApiResponse.Error -> updateUiState(BaseUIState.Error(response.msg))
                    is ApiResponse.Success -> {
                        reduce {
                            state.copy(
                                id = response.data!!.id,
                                imageUrl = response.data!!.imageUrl,
                                title = response.data!!.title,
                                price = response.data!!.price,
                                sizes = response.data!!.sizes ?: emptyList(),
                                material = response.data!!.material ?: "-",
                                standard = response.data!!.standard ?: "-",
                                typeOfStandard = response.data!!.typeOfStandard ?: "-",
                                insertion = response.data!!.insertion ?: "-",
                                weight = response.data!!.weight ?: "-",
                                article = response.data!!.article.toString(),
                                description = response.data!!.description,
                                isFavourite = response.data!!.isFavourite,
                            )
                        }
                        updateUiState(BaseUIState.Success)
                    }
                }
            }
        }
    }

    private fun changeFavouritesStatus() {
        viewModelScope.launch {
            intent {
                when (val response = changeFavoriteProductStatus(state.id!!, !state.isFavourite)) {
                    is ApiResponse.Error -> updateUiState(BaseUIState.Error(msg = response.msg))
                    is ApiResponse.Success -> {
                        reduce {
                            state.copy(isFavourite = !state.isFavourite)
                        }
                    }
                }
            }
        }
    }

    private fun addToCart() {
        viewModelScope.launch {
            intent {
                if (state.choosenSize == null && state.sizes.isNotEmpty()) {
                    updateUiState(BaseUIState.Error("Выберите размер"))
                    return@intent
                }
                if (state.id == null || (state.choosenSize == null && state.sizes.isNotEmpty()))
                    return@intent

                val response = addProductToCartInteractor(id = state.id!!, size = state.choosenSize, count = 1)

                when (response) {
                    is ApiResponse.Error -> updateUiState(BaseUIState.Error(response.msg))
                    is ApiResponse.Success -> { }
                }
            }
        }
    }

    private fun setChosenSize(size: String) {
        viewModelScope.launch {
            intent {
                reduce {
                    if (size != state.choosenSize)
                        state.copy(choosenSize = size)
                    else
                        state.copy(choosenSize = null)
                }
            }
        }
    }
}