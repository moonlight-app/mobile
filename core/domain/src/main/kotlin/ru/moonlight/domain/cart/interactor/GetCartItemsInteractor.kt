package ru.moonlight.domain.cart.interactor

import ru.moonlight.data.repository.CartRepository
import javax.inject.Inject

class GetCartItemsInteractor @Inject constructor(
    private val repository: CartRepository,
){
    fun getCartPagingDataFlow() = repository.getProductsInCartPagingData()
}