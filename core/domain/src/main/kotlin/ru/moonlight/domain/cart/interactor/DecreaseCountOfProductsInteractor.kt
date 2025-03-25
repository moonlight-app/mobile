package ru.moonlight.domain.cart.interactor

import ru.moonlight.data.api.repository.CartRepository
import javax.inject.Inject

class DecreaseCountOfProductsInteractor @Inject constructor(
    private val repository: CartRepository,
) {
    companion object {
        private const val MIN_PRODUCT_COUNT = 1
    }

    suspend operator fun invoke(itemId: Long, currentCount: Int) {
        if (currentCount == MIN_PRODUCT_COUNT) return;

        val count = calculateCount(currentCount)
        repository.updateCountOfProducts(itemId = itemId, count = count)
    }

    private fun calculateCount(currentCount: Int): Int {
        return if (isCountDecreasable(currentCount))
            currentCount - 1
        else
            MIN_PRODUCT_COUNT
    }

    private fun isCountDecreasable(count: Int): Boolean = (count != MIN_PRODUCT_COUNT)
}