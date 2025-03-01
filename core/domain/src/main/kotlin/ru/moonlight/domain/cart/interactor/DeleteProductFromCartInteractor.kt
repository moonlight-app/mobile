package ru.moonlight.domain.cart.interactor

import ru.moonlight.data.repository.CartRepository
import javax.inject.Inject

class DeleteProductFromCartInteractor @Inject constructor(
    private val repository: CartRepository,
) {
    suspend operator fun invoke(id: Long) = repository.deleteProductFromCartApiAndDatabase(id)
}